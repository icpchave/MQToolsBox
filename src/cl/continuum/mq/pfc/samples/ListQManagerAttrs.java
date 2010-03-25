package cl.continuum.mq.pfc.samples;
import java.io.*;

import com.ibm.mq.*;
import com.ibm.mq.pcf.*;

/**
 * PCF example class showing use of PCFAgent and com.ibm.mq.pcf structure types 
 * to generate and parse a PCF query.
 * 
 * @author Chris Markes
 */

public class ListQManagerAttrs
{
	final public static String copyright = "Copyright (c) IBM Corp. 1998, 2000   All rights reserved.";

	public static void main (String [] args) throws MQException, IOException
	{
		int [] 			attrs = { CMQCFC.MQIACF_ALL };
		PCFAgent 		agent;
		PCFParameter [] 	parameters = { new MQCFIL (CMQCFC.MQIACF_Q_MGR_ATTRS, attrs) };
		MQMessage [] 		responses;
		MQCFH 			cfh;
		PCFParameter 		p;

		
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
			responses = agent.send (CMQCFC.MQCMD_INQUIRE_Q_MGR, parameters);
			System.out.println ("Received reply.");
			cfh = new MQCFH (responses [0]);

			// Check the PCF header (MQCFH) in the first response message

			if (cfh.reason == 0)
			{
				System.out.println ("Queue manager attributes:");

				for (int i = 0; i < cfh.parameterCount; i++)
				{
					// Walk through the returned attributes

					p = PCFParameter.nextParameter (responses [0]);
					System.out.println ("\t attribute id " + 
						p.getParameter () + "=" + p.getValue ());
				}
			}
			else
			{
				System.out.println ("PCF error:\n" + cfh);

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
}