/**
 * 
 */
package cl.continuum.mqtoolkit.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

import cl.continuum.mqtoolkit.main.MQToolBox;
import cl.continuum.mqtoolkit.model.MQTableModel;
import cl.continuum.mqtoolkit.model.MessageData;
import cl.continuum.mqtoolkit.model.QManagerData;

/**
 * @author israel
 * 
 */
public class SenderThread extends Thread {

	private int nCiclos;
	private int nMessages;
	private MQToolBox tb;
	private MessageData mData;
	private QManagerData qmData;
	private String qName;
	private MQQueue queue;
	private MQQueueManager qm;
	private Date now;

	public SenderThread(int nCiclos, int nMessages, MQToolBox tb,
			MessageData mData, QManagerData qmData, String qName) {
		this.nCiclos = nCiclos;
		this.nMessages = nMessages;
		this.tb = tb;
		this.mData = mData;
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
		tb.console.setText("Ejecutandose SenderThread");
		int i = 0;
		int openOptions = MQC.MQOO_FAIL_IF_QUIESCING + MQC.MQOO_OUTPUT;
		try {
			connect();

			queue = qm.accessQueue(qName, openOptions);

			while (i < nCiclos) {
				putMessages();
				now = new Date();
				tb.console.setText("Cuando " + i + ": " + now.getTime());
				tb.loadResultsTableModel.setValueAt(now.getTime(), i, 0);
				tb.loadResultsTable.setModel(tb.loadResultsTableModel);
				tb.loadResultsTable.repaint();
				i++;
			}

			queue.close();
			qm.disconnect();

		} catch (MQException e) {
			manageError(e);
			return;
		} catch (IOException e) {
			manageError(e);
			return;
		}

	}

	private void putMessages() throws MQException, IOException {
		int j = 0;
		MQMessage mqmsg = new MQMessage();
		while (j < nMessages) {
			String data = mData.getDatos();
			data = data.replaceAll("#", String.valueOf(j));
			
			mqmsg.characterSet = mData.getCharSetID();
			mqmsg.clearMessage();
			mqmsg.writeString(data);
			mqmsg.replyToQueueManagerName = mData.getReplyToQManager();
			mqmsg.replyToQueueName = mData.getReplyToQ();
			//System.out.println("Expiry: " + mData.getCaducidad());
			if (mData.getCaducidad() != 0)
				mqmsg.expiry = (int) mData.getCaducidad();
			mqmsg.encoding = mData.getCodeCharSet();
			mqmsg.correlationId = toByteArray(mData.getCorrID());
			mqmsg.format = mData.getFormato();
			mqmsg.groupId = toByteArray(mData.getGroupID());
			mqmsg.messageId = toByteArray(mData.getMessID()); // Set the get
																// message
																// options...
			MQPutMessageOptions pmo = new MQPutMessageOptions();
			queue.put(mqmsg, pmo);
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
	private byte[] toByteArray(String hexStr) {
		byte msgId[] = new byte[hexStr.length() / 2];
		for (int i = 0; i < (hexStr.length() / 2); i++) {
			byte firstNibble = Byte.parseByte(hexStr
					.substring(2 * i, 2 * i + 1), 16);
			byte secondNibble = Byte.parseByte(hexStr.substring(2 * i + 1,
					2 * i + 2), 16);
			int finalByte = (secondNibble) | (firstNibble << 4);
			msgId[i] = (byte) finalByte;
		}
		return msgId;
	}

	private void manageError(Exception e){
		tb.loadResultsTableModel = new MQTableModel(
				new String[][] {}, new String[] { "Ciclo de Requerimiento",
						"Ciclo de Respuesta", "SLA del Ciclo" });
		tb.loadResultsTable.setModel(tb.loadResultsTableModel);
	}
	private void manageMQError(MQException e){
		tb.loadResultsTableModel = new MQTableModel(
				new String[][] {}, new String[] { "Ciclo de Requerimiento",
						"Ciclo de Respuesta", "SLA del Ciclo" });
		tb.loadResultsTable.setModel(tb.loadResultsTableModel);
		tb.console.setText("La prueba de cara ha fallado por el siguiente error:\nRC: "+e.reasonCode+". Mensaje: "+e.getMessage());
	}
}
