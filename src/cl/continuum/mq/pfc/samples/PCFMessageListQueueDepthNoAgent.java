package cl.continuum.mq.pfc.samples;
import java.io.*;
import com.ibm.mq.*;
import com.ibm.mq.pcf.*;

/**
 * PCF example where the application deals with the putting and getting of PCF request and responses 
 * directly. A PCFMessage is used to build the request and extract the PCF response information from the reply.
 * 
 * @author Chris Markes
 */

public class PCFMessageListQueueDepthNoAgent
{
	final public static String copyright = "Copyright (c) IBM Corp. 1998, 2000   All rights reserved.";

	public static void main (String [] args)
	{
		MQQueueManager 		qmanager;
		MQQueue			adminQueue, 
					replyQueue;
		String 			replyQueueName = "PCF_REPLY";
		MQPutMessageOptions 	pmo = new MQPutMessageOptions ();
		MQGetMessageOptions 	gmo = new MQGetMessageOptions ();
		MQMessage 		message;
		PCFMessage 		request, 
					response;	
		String 			name = null;
		Integer 		depth = null;

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

			message = new MQMessage ();
			message.messageType = MQC.MQMT_REQUEST;
			message.expiry = 100;
			message.feedback = MQC.MQFB_NONE;
			message.format = MQC.MQFMT_ADMIN;
			message.replyToQueueName = replyQueueName;

			request = new PCFMessage (CMQCFC.MQCMD_INQUIRE_Q);
			request.addParameter (CMQC.MQCA_Q_NAME, "*");
			request.addParameter (CMQC.MQIA_Q_TYPE, CMQC.MQQT_LOCAL);
			request.addParameter (CMQCFC.MQIACF_Q_ATTRS, 
				new int [] { CMQC.MQCA_Q_NAME, CMQC.MQIA_CURRENT_Q_DEPTH });
			request.write (message);

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
				response = new PCFMessage (message);

				// Check the reason code in the response message

				if (response.getReason () == CMQC.MQRC_NONE)
				{
					// Extract what we want from the returned attributes

					name = (String) response.getParameterValue (CMQC.MQCA_Q_NAME);
					depth = (Integer) response.getParameterValue (CMQC.MQIA_CURRENT_Q_DEPTH);

					System.out.println ("Queue " + name + " curdepth " + depth);
				}
				else
				{
					System.out.println ("PCF error:\n" + response);
				}
			}
			while (response.getControl () != CMQCFC.MQCFC_LAST);

			// Disconnect

			System.out.print ("Disconnecting... ");
			qmanager.disconnect ();
			System.out.println ("Done.");
		}

		catch (ArrayIndexOutOfBoundsException abe)
		{
			System.out.println ("Usage: \n" + 
				"\tjava ListQueueDepthNoAgent2 queue-manager\n" + 
				"\tjava ListQueueDepthNoAgent2 host port channel");
		}

		catch (NumberFormatException nfe)
		{
			System.out.println ("Invalid port: " + args [1]);
			System.out.println ("Usage: \n" + 
				"\tjava ListQueueDepthNoAgent2 queue-manager\n" + 
				"\tjava ListQueueDepthNoAgent2 host port channel");
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