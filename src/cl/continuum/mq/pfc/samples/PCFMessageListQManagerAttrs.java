package cl.continuum.mq.pfc.samples;
import java.io.*;
import java.util.*;
import com.ibm.mq.*;
import com.ibm.mq.pcf.*;

/**
 * Example using PCFMessage and PCFMessageAgent classes to generate and send 
 * a PCF request and extract information from the response. 
 * 
 * @author Chris Markes
 */

public class PCFMessageListQManagerAttrs
{
	final public static String copyright = "Copyright (c) IBM Corp. 1998, 2000   All rights reserved.";

	public static void main (String [] args)
	{
		try
		{
			PCFMessageAgent agent;
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

			// Build the PCF request

			request = new PCFMessage (CMQCFC.MQCMD_INQUIRE_Q_MGR);
			request.addParameter (CMQCFC.MQIACF_Q_MGR_ATTRS, new int [] { CMQCFC.MQIACF_ALL });

			// Use the agent to send the request

			System.out.print ("Sending PCF request... ");
			responses = agent.send (request);
			System.out.println ("Received reply.");

			// Display the results

			System.out.println ("Queue manager attributes:");

			Enumeration 	e = responses [0].getParameters ();

			while (e.hasMoreElements ())
			{
				PCFParameter 	p = (PCFParameter) e.nextElement ();

				System.out.println ("\t attribute id " + 
					p.getParameter () + "=" + p.getValue ());
			}

			// Disconnect

			System.out.print ("Disconnecting... ");
			agent.disconnect ();
			System.out.println ("Done.");
		}

		catch (ArrayIndexOutOfBoundsException abe)
		{
			System.out.println ("Usage: \n" + 
				"\tjava PCFMessageListQManagerAttrs queue-manager\n" + 
				"\tjava PCFMessageListQManagerAttrs host port channel");
		}

		catch (NumberFormatException nfe)
		{
			System.out.println ("Invalid port: " + args [1]);
			System.out.println ("Usage: \n" + 
				"\tjava PCFMessageListQManagerAttrs queue-manager\n" + 
				"\tjava PCFMessageListQManagerAttrs host port channel");
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