package cl.continuum.mq.pfc.samples;
import java.io.*;
import com.ibm.mq.*;
import com.ibm.mq.pcf.*;

/**
 * PCF example class showing use of PCFMessageAgent to generate and parse a 
 * PCF query. Note that the list of queue names returned includes the dynamic 
 * queue used by the PCFMessageAgent; the name of this queue is accessible as 
 * the <em>replyQueueName</em> field of the PCFMessageAgent object.
 * 
 * @author Chris Markes
 */

public class PCFMessageListQueueNames
{
	final public static String copyright = "Copyright (c) IBM Corp. 1998   All rights reserved.";

	public static void main (String [] args)
	{
		try
		{
			PCFMessageAgent	agent;
			PCFMessage 	request;
			PCFMessage [] 	responses;
			String [] 	names;

			// Connect a PCFAgent to the specified queue manager

			if (args.length == 1)
			{
				System.out.print ("Connecting to local queue manager " + 
					args [0] + "... ");
				agent = new PCFMessageAgent (args [0]);
			}
			else
			{
				System.out.print ("Connecting to queue manager at " + 
					args [0] + ":" + args [1] + " over channel " + args [2] + "... ");
				agent = new PCFMessageAgent (args [0], Integer.parseInt (args [1]), args [2]);
			}

			System.out.println ("Connected.");

			// Build the PCF request

			request = new PCFMessage (CMQCFC.MQCMD_INQUIRE_Q_NAMES);
			request.addParameter (CMQC.MQCA_Q_NAME, "*");
			request.addParameter (CMQC.MQIA_Q_TYPE, CMQC.MQQT_ALL);

			System.out.print ("Sending PCF request... ");

			// Use the agent to send the request

			responses = agent.send (request);
			System.out.println ("Received reply.");

			// Extract the MQCACF_Q_NAMES parameter from the response

			names = (String []) responses [0].getParameterValue (CMQCFC.MQCACF_Q_NAMES);

			// Display the results

			System.out.println ("Queue names:");

			for (int i = 0; i < names.length; i++)
			{
				System.out.println ("\t" + names [i]);
			}

			// Disconnect the agent

			System.out.print ("Disconnecting... ");
			agent.disconnect ();
			System.out.println ("Done.");
		}

		catch (ArrayIndexOutOfBoundsException abe)
		{
			System.out.println ("Usage: \n" + 
				"\tjava PCFMessageListQueueNames queue-manager\n" + 
				"\tjava PCFMessageListQueueNames host port channel");
		}

		catch (NumberFormatException nfe)
		{
			System.out.println ("Invalid port: " + args [1]);
			System.out.println ("Usage: \n" + 
				"\tjava PCFMessageListQueueNames queue-manager\n" + 
				"\tjava PCFMessageListQueueNames host port channel");
		}

		catch (PCFException pcfe)
		{
			System.err.println ("Error in response: ");

			PCFMessage [] 	responses = (PCFMessage []) pcfe.exceptionSource;

			for (int i = 0; i < responses.length; i++)
			{
				System.out.println (responses [i]);
			}
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