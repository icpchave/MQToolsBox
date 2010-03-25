/**
 * 
 */
package cl.continuum.mqtoolkit.impl;

import java.util.Date;

import cl.continuum.mqtoolkit.main.MQToolBox;
import cl.continuum.mqtoolkit.model.MQTableModel;
import cl.continuum.mqtoolkit.model.QManagerData;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

/**
 * @author israel
 * 
 */
public class GetterThread extends Thread {
	private int nCiclos;
	private int nMessages;
	private MQToolBox tb;
	private QManagerData qmData;
	private String qName;
	private MQQueue queue;
	private MQQueueManager qm;
	private Date now;
	private int waitInterval;

	public GetterThread(int nCiclos, int nMessages, MQToolBox tb,
			QManagerData qmData, String qName, int wait) {
		super();
		this.nCiclos = nCiclos;
		this.nMessages = nMessages;
		this.tb = tb;
		this.qmData = qmData;
		this.qName = qName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		int i = 0;
		long put;

		try {
			connect();
			while (i < nCiclos) {
				getMessages();
				now = new Date();
				tb.loadResultsTableModel.setValueAt(now.getTime(), i, 1);
				System.out.println("$$$$$$$$$ "
						+ ((Long) tb.loadResultsTable.getValueAt(i, 0))
								.longValue());
				put = ((Long) tb.loadResultsTable.getValueAt(i, 0)).longValue();
				tb.loadResultsTableModel
						.setValueAt((now.getTime() - put), i, 2);
				tb.loadResultsTable.setModel(tb.loadResultsTableModel);
				tb.loadResultsTable.repaint();
				i++;
			}
			queue.close();
			qm.disconnect();
		} catch (MQException e) {
			manageError(e);
			return;
		}
	}

	private void getMessages() throws MQException {
		int j = 0;
		MQMessage mqmsg = new MQMessage();
		int openOptionsResp = MQC.MQOO_INQUIRE + MQC.MQOO_FAIL_IF_QUIESCING
				+ MQC.MQOO_INPUT_SHARED;
		queue = qm.accessQueue(qName, openOptionsResp);

		// Set the get message options...
		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = MQC.MQGMO_WAIT + MQC.MQGMO_FAIL_IF_QUIESCING;
		gmo.waitInterval = waitInterval;
		
		while (j < nMessages) {
			mqmsg = new MQMessage();
			queue.get(mqmsg, gmo);
			j++;
		}
	}

	private void connect() throws MQException {
		tb.console.setText("Conectandose a: " + qmData.getName() + "@"
				+ qmData.getHost() + ":" + qmData.getPort() + " Canal: "
				+ qmData.getChannel());
		MQEnvironment.hostname = qmData.getHost();
		MQEnvironment.channel = qmData.getChannel().toUpperCase();
		MQEnvironment.port = qmData.getPort();
		qm = new MQQueueManager(qmData.getName());
		tb.console.setText("Conectado");
	}
	
	private void manageError(MQException e){
		tb.loadResultsTableModel = new MQTableModel(
				new String[][] {}, new String[] { "Ciclo de Requerimiento",
						"Ciclo de Respuesta", "SLA del Ciclo" });
		tb.loadResultsTable.setModel(tb.loadResultsTableModel);
		tb.console.setText("La prueba de cara ha fallado por el siguiente error:\nRC: "+e.reasonCode+". Mensaje: "+e.getMessage());
	}

}
