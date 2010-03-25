package cl.continuum.mq.pfc.samples;

import java.io.*;
import com.ibm.mq.*;
import com.ibm.mq.pcf.*;

/**
 * PCF example class showing use of a PCF filter parameter. This class issues an inquire queue 
 * command including a filter parameter so that only queues with a current queue depth greater 
 * than zero are returned.
 * 
 * @author Chris Markes
 */

public class ListActiveQueues
{
	public static void main (String [] args)
    {
        try
        {
            PCFMessageAgent agent;
            
			if (args.length == 1)
			{
				// Local queue manager connection (queue manager name).
				
				agent = new PCFMessageAgent (args [0]);
			}
			else
			{
				// Client connection (host, port, channel).
				
				agent = new PCFMessageAgent (args [0], Integer.parseInt (args [1]), args [2]);
			}

			PCFMessage request = new PCFMessage (CMQCFC.MQCMD_INQUIRE_Q);

            request.addParameter (CMQC.MQCA_Q_NAME, "*");
            request.addParameter (CMQC.MQIA_Q_TYPE, MQC.MQQT_LOCAL);
            request.addFilterParameter (CMQC.MQIA_CURRENT_Q_DEPTH, CMQCFC.MQCFOP_GREATER, 0);

            PCFMessage [] responses = agent.send (request);

            for (int i = 0; i < responses.length; i++)
            {
            	PCFMessage response = responses [i];
            	
                System.out.println ("Queue " + response.getParameterValue (CMQC.MQCA_Q_NAME) + " depth " + 
                	response.getParameterValue (CMQC.MQIA_CURRENT_Q_DEPTH));
            }
            
            System.out.println (responses.length + 
            	(responses.length == 1 ? " active queue" : " active queues"));
        }

        catch (MQException mqe)
        {
            System.err.println (mqe + ": " + PCFConstants.lookupReasonCode (mqe.reasonCode));
        }

        catch (IOException ioe)
        {
            System.err.println (ioe);
        }

        catch (ArrayIndexOutOfBoundsException abe)
        {
            System.err.println ("Usage: java " + ListActiveQueues.class.getName () + 
            	" local-queue-manager-name | host port channel");
        }
    }
}