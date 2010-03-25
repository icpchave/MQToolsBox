/**
 * 
 */
package cl.continuum.mq.pfc.tester;

import java.io.IOException;

import cl.continuum.mqtoolkit.impl.MQPFCImpl;
import cl.continuum.mqtoolkit.model.QManagerData;

import com.ibm.mq.*;

/**
 * @author Israel Cruz
 *
 */
public class MQTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MQPFCImpl impl = new MQPFCImpl();
		QManagerData qmData = new QManagerData();
		qmData.setName("MILHOUSE_QM");
		qmData.setHost("localhost");
		qmData.setPort(1414);
		qmData.setChannel("MQSERVER");
		try {
			impl.listActivesQueues(qmData);
			System.out.println("**************************************************************************************************");
			System.out.println("**************************************************************************************************");
			impl.listQMgrsAttrs(qmData);
			System.out.println("**************************************************************************************************");
			System.out.println("**************************************************************************************************");
			impl.listQueueDepth(qmData);
			System.out.println("**************************************************************************************************");
			System.out.println("**************************************************************************************************");
			impl.listQueueNames(qmData);
			System.out.println("**************************************************************************************************");
			System.out.println("**************************************************************************************************");
			//impl.
		} catch (MQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*try {
		MQEnvironment.hostname = "localhost";
		MQEnvironment.channel = "MQSERVER";
		MQEnvironment.port = 1414;
		
		MQQueueManager qmgr = new MQQueueManager("MILHOUSE_QM", MQC.MQCNO_NONE) ;
		
		System.out.println("Conectado: " + qmgr.isOpen());
		
		
		
		} catch (MQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

}
