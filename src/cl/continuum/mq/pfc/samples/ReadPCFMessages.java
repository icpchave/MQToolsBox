package cl.continuum.mq.pfc.samples;
import java.io.IOException;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.pcf.PCFConstants;
import com.ibm.mq.pcf.PCFMessage;

/**
 * Simple program for reading PCF-format messages from a queue. This includes the queues and message types 
 * shown below, in addition to PCF administration command and response messages.
 * <p>
 * <table>
 * <tr><th>Queue</th><th>Message type</th></tr>
 * <tr><td>SYSTEM.ADMIN.QMGR.EVENT</td><td>Event messages</td></tr>
 * <tr><td>SYSTEM.ADMIN.PERFM.EVENT</td><td>Event messages</td></tr>
 * <tr><td>SYSTEM.ADMIN.CHANNEL.EVENT</td><td>Event messages</td></tr>
 * <tr><td>SYSTEM.ADMIN.ACCOUNTING.QUEUE</td><td>Accounting messages</td></tr>
 * <tr><td>SYSTEM.ADMIN.STATISTICS.QUEUE</td><td>Statistics messages</td></tr>
 * </table>
 * 
 * @author Chris Markes
 */
public class ReadPCFMessages
{
	public static void main (String [] args)
    {
		int messageCount = 0;
		
        try
        {
        	MQException.log = null;
            MQQueueManager qm = new MQQueueManager (args [0]);
            MQQueue queue = qm.accessQueue (args [1], MQC.MQOO_BROWSE | MQC.MQOO_FAIL_IF_QUIESCING);
            MQMessage message = new MQMessage ();
            MQGetMessageOptions gmo = new MQGetMessageOptions ();
            
            gmo.options = MQC.MQGMO_BROWSE_NEXT | MQC.MQGMO_NO_WAIT | MQC.MQGMO_CONVERT;
            
            while (true)
            {
            	message.messageId = null;
            	message.correlationId = null;
            	queue.get (message, gmo);
            	
            	// Parse the message content using a PCFMessage object and print out the result.
            	
            	PCFMessage pcf = new PCFMessage (message);
            	
                System.out.println ("Message " + ++messageCount + ": " + pcf + "\n");
            }
        }

        catch (MQException mqe)
        {
        	if (mqe.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE)
        	{
        		System.out.println (messageCount + (messageCount == 1 ? " message." : " messages."));
        	}
        	else
        	{
        		System.err.println (mqe + ": " + PCFConstants.lookupReasonCode (mqe.reasonCode));
        	}
        }

        catch (IOException ioe)
        {
            System.err.println (ioe);
        }

        catch (ArrayIndexOutOfBoundsException abe)
        {
            System.err.println ("Usage: java " + ReadPCFMessages.class.getName () + 
            	" local-queue-manager-name queue-name");
        }
    }
}
