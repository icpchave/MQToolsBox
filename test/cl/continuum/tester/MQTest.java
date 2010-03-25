package cl.continuum.tester;

import java.io.IOException;
import java.util.Hashtable;

import com.ibm.mq.MQException;

import cl.continuum.mqtoolkit.impl.ToolsImpl;
import cl.continuum.mqtoolkit.model.QManagerData;

public class MQTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Hashtable options = new Hashtable();
		ToolsImpl ti = new ToolsImpl();
		QManagerData qmData = new QManagerData();
		qmData.setName("MILHOUSE_QM");
		qmData.setHost("localhost");
		qmData.setPort(1414);
		qmData.setChannel("MQSERVER");
		System.out.println("Imprimiendo");
		try {
			ti.listQueuesNames(qmData );
			System.out.println("*******************************");
			ti.listQueuesNames(qmData.getName());
		} catch (MQException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
