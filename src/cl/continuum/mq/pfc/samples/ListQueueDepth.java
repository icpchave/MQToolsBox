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

public class ListQueueDepth {
	final public static String copyright = "Copyright (c) IBM Corp. 1998, 2000   All rights reserved.";

	public static void main(String[] args) {
		PCFAgent agent;
		int[] attrs = { CMQC.MQCA_Q_NAME, CMQC.MQIA_CURRENT_Q_DEPTH,
				CMQC.MQIA_MAX_MSG_LENGTH, CMQC.MQIA_OPEN_INPUT_COUNT,
				CMQC.MQIA_OPEN_OUTPUT_COUNT };
		PCFParameter[] parameters = { new MQCFST(CMQC.MQCA_Q_NAME, "*"),
				new MQCFIN(CMQC.MQIA_Q_TYPE, CMQC.MQQT_ALL),
				new MQCFIL(CMQCFC.MQIACF_Q_ATTRS, attrs) };
		MQMessage[] responses;
		MQCFH cfh;
		PCFParameter p;
		String name = null;
		Integer depth = null;
		String reqBacoutQName = null;
		Integer openInput = null;
		Integer openOutput = null;
		String type = null;
		Integer maxMsgLength = null;
		int qTypeN = 0;

		try {
			// Connect a PCFAgent to the specified queue manager

			if (args.length == 1) {
				System.out.print("Connecting to local queue manager " + args[0]
						+ "... ");
				agent = new PCFAgent(args[0]);
			} else {
				System.out.print("Connecting to queue manager at " + args[0]
						+ ":" + args[1] + " over channel " + args[2] + "... ");
				agent = new PCFAgent(args[0], Integer.parseInt(args[1]),
						args[2]);
			}

			System.out.println("Connected.");

			// Use the agent to send the request

			System.out.print("Sending PCF request... ");
			responses = agent.send(CMQCFC.MQCMD_INQUIRE_Q, parameters);
			System.out.println("Received reply.");

			for (int i = 0; i < responses.length; i++) {
				cfh = new MQCFH(responses[i]);

				// Check the PCF header (MQCFH) in the response message

				if (cfh.reason == 0) {
					for (int j = 0; j < cfh.parameterCount; j++) {
						// Extract what we want from the returned attributes

						p = PCFParameter.nextParameter(responses[i]);

						switch (p.getParameter()) {
						case CMQC.MQCA_Q_NAME:
							name = (String) p.getValue();
							break;
						case CMQC.MQIA_CURRENT_Q_DEPTH:
							depth = (Integer) p.getValue();
							break;
						case CMQC.MQCA_BACKOUT_REQ_Q_NAME:
							reqBacoutQName = (String) p.getValue();
							break;
						case CMQC.MQIA_Q_TYPE:
							qTypeN = ((Integer) p.getValue()).intValue();
							if (CMQC.MQQT_LOCAL == qTypeN)
								type = "Local";
							if (CMQC.MQQT_ALIAS == qTypeN)
								type = "Alias";
							if (CMQC.MQQT_CLUSTER == qTypeN)
								type = "Cluster";
							if (CMQC.MQQT_REMOTE == qTypeN)
								type = "Remote";
							break;
						case CMQC.MQIA_MAX_MSG_LENGTH:
							maxMsgLength = (Integer) p.getValue();
							break;
						case CMQC.MQIA_OPEN_INPUT_COUNT:
							openInput = (Integer) p.getValue();
							break;
						case CMQC.MQIA_OPEN_OUTPUT_COUNT:
							openOutput = (Integer) p.getValue();
							break;

						/*
						 * 
						 */
						default:
						}
					}

					System.out.println("Queue " + name + " curdepth " + depth
							+ " BackOut Queue Name " + reqBacoutQName
							+ " tipo " + type + " MaxMsgLength " + maxMsgLength
							+ " OpenInput " + openInput + " OpenOutput "
							+ openOutput);
				} else {
					System.out.println("PCF error:\n" + cfh);

					// Walk through the returned parameters describing the error

					for (int j = 0; j < cfh.parameterCount; j++) {
						System.out.println(PCFParameter
								.nextParameter(responses[0]));
					}
				}
			}

			// Disconnect

			System.out.print("Disconnecting... ");
			agent.disconnect();
			System.out.println("Done.");
		}

		catch (ArrayIndexOutOfBoundsException abe) {
			System.out.println("Usage: \n"
					+ "\tjava ListQueueDepth queue-manager\n"
					+ "\tjava ListQueueDepth host port channel");
		}

		catch (NumberFormatException nfe) {
			System.out.println("Invalid port: " + args[1]);
			System.out.println("Usage: \n"
					+ "\tjava ListQueueDepth queue-manager\n"
					+ "\tjava ListQueueDepth host port channel");
		}

		catch (MQException mqe) {
			System.err.println(mqe);
		}

		catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}