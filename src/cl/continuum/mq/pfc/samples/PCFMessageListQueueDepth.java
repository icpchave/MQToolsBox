package cl.continuum.mq.pfc.samples;
import java.io.*;
import com.ibm.mq.*;
import com.ibm.mq.pcf.*;

/**
 * PCF example class showing use of PCFMessageAgent and PCFMessage to generate and parse a PCF query.
 * 
 * @author Chris Markes
 */

public class PCFMessageListQueueDepth
{
	final public static String copyright = "Copyright (c) IBM Corp. 1998, 2000   All rights reserved.";

	public static void main (String [] args)
	{
		try
		{
			PCFMessageAgent	agent;
			PCFMessage 	request;
			PCFMessage [] 	responses;

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

			// Build the request

			request = new PCFMessage (CMQCFC.MQCMD_INQUIRE_Q);
			request.addParameter (CMQC.MQCA_Q_NAME, "*");
			request.addParameter (CMQC.MQIA_Q_TYPE, CMQC.MQQT_LOCAL);
			request.addParameter (CMQCFC.MQIACF_Q_ATTRS, 
				new int [] { CMQC.MQCA_Q_NAME, CMQC.MQIA_CURRENT_Q_DEPTH });

			// Use the agent to send the request

			System.out.print ("Sending PCF request... ");
			responses = agent.send (request);
			System.out.println ("Received reply.");

			// Display the results

			for (int i = 0; i < responses.length; i++)
			{
				String 		name = responses [i].getStringParameterValue (CMQC.MQCA_Q_NAME);
				int 		depth = responses [i].getIntParameterValue (CMQC.MQIA_CURRENT_Q_DEPTH);

				System.out.println ("Queue " + name + " curdepth " + depth);
			}

			// Disconnect

			System.out.print ("Disconnecting... ");
			agent.disconnect ();
			System.out.println ("Done.");
		}

		catch (ArrayIndexOutOfBoundsException abe)
		{
			System.out.println ("Usage: \n" + 
				"\tjava PCFMessageListQueueDepth queue-manager\n" + 
				"\tjava PCFMessageListQueueDepth host port channel");
		}

		catch (NumberFormatException nfe)
		{
			System.out.println ("Invalid port: " + args [1]);
			System.out.println ("Usage: \n" + 
				"\tjava PCFMessageListQueueDepth queue-manager\n" + 
				"\tjava PCFMessageListQueueDepth host port channel");
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