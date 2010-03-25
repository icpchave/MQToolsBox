package cl.continuum.mq.pfc.samples;
import java.io.*;
import com.ibm.mq.*;
import com.ibm.mq.pcf.*;

/**
 * PCF example class showing use of PCFAgent and com.ibm.mq.pcf structure types 
 * to generate and parse a PCF query. Note that the list of queue names returned 
 * includes the dynamic queue used by the PCFAgent; the name of this queue is 
 * accessible as the replyQueueName field of the PCFAgent object.
 * 
 * @author Chris Markes
 */

public class ListQueueNames
{
	final public static String copyright = "Copyright (c) IBM Corp. 1998   All rights reserved.";

	public static void main (String [] args)
	{
		PCFAgent 		agent;
		PCFParameter [] 	parameters = 
					{
						new MQCFST (CMQC.MQCA_Q_NAME, "*"), 
						new MQCFIN (CMQC.MQIA_Q_TYPE, CMQC.MQQT_ALL)
					};
		MQMessage [] 		responses;
		MQCFH 			cfh;
		MQCFSL 			cfsl;

		try
		{
			// Connect a PCFAgent to the specified queue manager

			if (args.length == 1)
			{
				System.out.print ("Connecting to local queue manager " + 
					args [0] + "... ");
				agent = new PCFAgent (args [0]);
			}
			else
			{
				System.out.print ("Connecting to queue manager at " + 
					args [0] + ":" + args [1] + " over channel " + args [2] + "... ");
				agent = new PCFAgent (args [0], Integer.parseInt (args [1]), args [2]);
			}

			System.out.println ("Connected.");

			// Use the agent to send the request

			System.out.print ("Sending PCF request... ");
			responses = agent.send (CMQCFC.MQCMD_INQUIRE_Q_NAMES, parameters);
			System.out.println ("Received reply.");
			cfh = new MQCFH (responses [0]);

			// Check the PCF header (MQCFH) in the first response message

			if (cfh.reason == 0)
			{
				System.out.println ("Queue names:");
				cfsl = new MQCFSL (responses [0]);

				for (int i = 0; i < cfsl.strings.length; i++)
				{
					System.out.println ("\t" + cfsl.strings [i]);
				}
			}
			else
			{
				System.out.println (cfh);

				// Walk through the returned parameters describing the error

				for (int i = 0; i < cfh.parameterCount; i++)
				{
					System.out.println (PCFParameter.nextParameter (responses [0]));
				}
			}

			// Disconnect

			System.out.print ("Disconnecting... ");
			agent.disconnect ();
			System.out.println ("Done.");
		}

		catch (ArrayIndexOutOfBoundsException abe)
		{
			System.out.println ("Usage: \n" + 
				"\tjava ListQueueNames queue-manager\n" + 
				"\tjava ListQueueNames host port channel");
		}

		catch (NumberFormatException nfe)
		{
			System.out.println ("Invalid port: " + args [1]);
			System.out.println ("Usage: \n" + 
				"\tjava ListQueueNames queue-manager\n" + 
				"\tjava ListQueueNames host port channel");
		}

		catch (MQException mqe)
		{
			System.err.println (mqe);
		}

		catch (IOException ioe)
		{
			System.err.println (ioe);
		}
	}
}