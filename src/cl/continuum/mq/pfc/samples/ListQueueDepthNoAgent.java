package cl.continuum.mq.pfc.samples;
import java.io.*;
import com.ibm.mq.*;
import com.ibm.mq.pcf.*;

/**
 * PCF example class showing use of com.ibm.mq.pcf structure types without a PCFAgent 
 * to generate and parse a PCF query.
 * 
 * @author Chris Markes
 */

public class ListQueueDepthNoAgent
{
	final public static String copyright = "Copyright (c) IBM Corp. 1998   All rights reserved.";

	public static void main (String [] args)
	{
		MQQueueManager 		qmanager;
		MQQueue			adminQueue, 
					replyQueue;
		String 			replyQueueName = "PCF_REPLY";
		MQPutMessageOptions 	pmo = new MQPutMessageOptions ();
		MQGetMessageOptions 	gmo = new MQGetMessageOptions ();
		MQMessage 		message = new MQMessage ();	
		String 			name = null;
		Integer 		depth = null;

		int [] 			attrs = 
					{ 
						CMQC.MQCA_Q_NAME, 
						CMQC.MQIA_CURRENT_Q_DEPTH
					};
		MQCFH 			cfh;
		PCFParameter 		p;

		try
		{
			// Turn off unnecessary output before we start

			MQEnvironment.disableTracing ();
			MQException.log = null;

			// Connect to the specified queue manager and open queues

			if (args.length == 1)
			{
				System.out.print ("Connecting to local queue manager " + 
					args [0] + "... ");
				qmanager = new MQQueueManager (args [0]);
			}
			else
			{
				System.out.print ("Connecting to queue manager at " + 
					args [0] + ":" + args [1] + " over channel " + args [2] + "... ");
				MQEnvironment.hostname = args [0];
				MQEnvironment.port = Integer.parseInt (args [1]);
				MQEnvironment.channel = args [2].toUpperCase();
				qmanager = new MQQueueManager ("");
			}

			System.out.println ("Connected.");

			// Open PCF request and reply queues

			adminQueue = qmanager.accessQueue (qmanager.getCommandInputQueueName (), 
				MQC.MQOO_OUTPUT, "", "", "mqm");
			replyQueue = qmanager.accessQueue ("SYSTEM.DEFAULT.MODEL.QUEUE", 
				MQC.MQOO_INPUT_EXCLUSIVE, "", replyQueueName, "mqm");

			// Generate and send the request

			message.messageType = MQC.MQMT_REQUEST;
			message.expiry = 100;
			message.feedback = MQC.MQFB_NONE;
			message.format = MQC.MQFMT_ADMIN;
			message.replyToQueueName = replyQueueName;

			MQCFH.write (message, CMQCFC.MQCMD_INQUIRE_Q, 3);   // 3 parameters follow
			MQCFST.write (message, CMQC.MQCA_Q_NAME, "*");
			MQCFIN.write (message, CMQC.MQIA_Q_TYPE, CMQC.MQQT_LOCAL);
			MQCFIL.write (message, CMQCFC.MQIACF_Q_ATTRS, attrs);

			System.out.print ("Sending PCF request... ");
			adminQueue.put (message, pmo);
			System.out.println ("Sent.");

			// Make sure we won't miss the response if it takes a while

			gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_CONVERT;
			gmo.waitInterval = 100;

			// Read all the response messages from the reply-to queue

			do
			{
				message.messageId = MQC.MQMI_NONE;
				replyQueue.get (message, gmo);
				cfh = new MQCFH (message);

				// Check the PCF header (MQCFH) in the response message

				if (cfh.reason == 0)
				{
					for (int i = 0; i < cfh.parameterCount; i++)
					{
						// Extract what we want from the returned attributes

						p = PCFParameter.nextParameter (message);

						switch (p.getParameter ())
						{
						case CMQC.MQCA_Q_NAME:
							name = (String) p.getValue ();
							break;
						case CMQC.MQIA_CURRENT_Q_DEPTH:
							depth = (Integer) p.getValue ();
							break;
						default:
						}
					}

					System.out.println ("Queue " + name + " curdepth " + depth);
				}
				else
				{
					System.out.println ("PCF error:\n" + cfh);

					// Walk through the returned parameters describing the error

					for (int i = 0; i < cfh.parameterCount; i++)
					{
						System.out.println (PCFParameter.nextParameter (message));
					}
				}
			}
			while (cfh.control != CMQCFC.MQCFC_LAST);

			// Disconnect

			System.out.print ("Disconnecting... ");
			qmanager.disconnect ();
			System.out.println ("Done.");
		}

		catch (ArrayIndexOutOfBoundsException abe)
		{
			System.out.println ("Usage: \n" + 
				"\tjava ListQueueDepthNoAgent queue-manager\n" + 
				"\tjava ListQueueDepthNoAgent host port channel");
		}

		catch (NumberFormatException nfe)
		{
			System.out.println ("Invalid port: " + args [1]);
			System.out.println ("Usage: \n" + 
				"\tjava ListQueueDepthNoAgent queue-manager\n" + 
				"\tjava ListQueueDepthNoAgent host port channel");
		}

		catch (MQException mqe)
		{
			if (mqe.reasonCode != MQException.MQRC_NO_MSG_AVAILABLE)
			{
				System.err.println (mqe);
			}
		}

		catch (IOException ioe)
		{
			System.err.println (ioe);
		}
	}
}