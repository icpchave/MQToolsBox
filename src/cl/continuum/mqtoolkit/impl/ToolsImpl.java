package cl.continuum.mqtoolkit.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import cl.continuum.mqtoolkit.model.MQTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import cl.continuum.mqtoolkit.model.MessageData;
import cl.continuum.mqtoolkit.model.QManagerData;
import cl.continuum.mqtoolkit.model.QueueData;
import cl.continuum.mqtoolkit.util.FileUtil;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.pcf.CMQC;
import com.ibm.mq.pcf.CMQCFC;
import com.ibm.mq.pcf.MQCFH;
import com.ibm.mq.pcf.MQCFIN;
import com.ibm.mq.pcf.MQCFSL;
import com.ibm.mq.pcf.MQCFST;
import com.ibm.mq.pcf.PCFAgent;
import com.ibm.mq.pcf.PCFParameter;

/**
 * Continuum
 * 
 * @author israel.cruz@continuum.cl
 * 
 * 
 */
public class ToolsImpl {

	// private Hashtable properties;
	private String errString = "";
	FileUtil fUtil = new FileUtil();
	List<QManagerData> qmsData;
	MQPFCImpl pfcImpl = new MQPFCImpl();

	public String getErrorString() {
		return errString;
	}

	private void disconnect(MQQueue queue, MQQueueManager qMgr)
			throws MQException {
		// Close the queue...
		queue.close();
		// Disconnect from the queue manager
		qMgr.disconnect();
	}

	private void disconnect(MQQueueManager qMgr) throws MQException {
		// Disconnect from the queue manager
		qMgr.disconnect();
	}

	public MQMessage getMessage(String qm, String qn, Hashtable properties)
			throws MQException {
		MQMessage retrievedMessage = new MQMessage();
		MQQueueManager qMgr = new MQQueueManager(qm, properties);
		int openOptions = MQC.MQOO_FAIL_IF_QUIESCING + MQC.MQOO_INPUT_SHARED;

		// Now specify the queue that we wish to open, and the open
		// options...
		MQQueue queue = qMgr.accessQueue(qn, openOptions);

		// Set the get message options...
		MQGetMessageOptions gmo = new MQGetMessageOptions();// accept the
		// defaults

		// get the message off the queue...
		queue.get(retrievedMessage, gmo);

		disconnect(queue, qMgr);

		// Returning message
		return retrievedMessage;
	}

	public void putMessage(String qm, String qn, MQMessage message,
			Hashtable properties) throws MQException {

		MQQueueManager qMgr = new MQQueueManager(qm, properties);
		int openOptions = MQC.MQOO_FAIL_IF_QUIESCING + MQC.MQOO_OUTPUT;

		// Now specify the queue that we wish to open, and the open
		// options...
		MQQueue queue = qMgr.accessQueue(qn, openOptions);

		// Set the put message options...
		MQPutMessageOptions pmo = new MQPutMessageOptions();

		// put the message on the queue...
		queue.put(message, pmo);

		// Close the queue...
		queue.close();
		// Disconnect from the queue manager
		qMgr.disconnect();

	}

	public boolean getMessages(Hashtable properties, String qm, String qn,
			String dirName, String baseName) {
		ArrayList msgList = new ArrayList();
		boolean status = true;
		try {
			MQQueueManager qMgr = new MQQueueManager(qm, properties);
			int openOptions = MQC.MQOO_INQUIRE + MQC.MQOO_FAIL_IF_QUIESCING
					+ MQC.MQOO_INPUT_SHARED;
			MQQueue queue = qMgr.accessQueue(qn, openOptions);
			int depth = queue.getCurrentDepth();
			if (depth == 0) {
				errString = "no messages to browse...";
				return false;
			}

			// Set the get message options...
			MQGetMessageOptions gmo = new MQGetMessageOptions();
			gmo.options = MQC.MQGMO_NO_WAIT + MQC.MQGMO_FAIL_IF_QUIESCING;

			String message = "";
			while (true) {
				MQMessage retrievedMessage = new MQMessage();
				try {
					queue.get(retrievedMessage, gmo);
					retrievedMessage.characterSet = ((Integer) properties
							.get("mqcharset")).intValue();
					int length = retrievedMessage.getMessageLength();
					message = retrievedMessage.readString(length
							/ ((Integer) properties.get("mqcharlength"))
									.intValue());
					msgList.add(message);
					retrievedMessage.clearMessage();
				} catch (IOException ex) {
					errString = "IOException during GET: " + ex.getMessage();
					status = false;
					break;
				} catch (MQException ex) {
					if (!(ex.completionCode == 2 && ex.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE)) {
						errString = "GET Exception: " + ex;
						status = false;
					}
					break;
				}
			}// end of while
			queue.close();
			qMgr.disconnect();
		}
		// If an error has occurred in the above,try to identify what went wrong
		catch (MQException ex) {
			errString = "An MQSeries error occurred :Completion code "
					+ ex.completionCode + " Reason code " + ex.reasonCode;
			status = false;
		}

		return status;

	}

	public boolean browseMessages(String qm, String qn, String dirName,
			String baseName, Hashtable properties) {
		ArrayList msgList = new ArrayList();
		boolean status = true;
		try {
			MQQueueManager qMgr = new MQQueueManager(qm, properties);
			int openOptions = MQC.MQOO_INQUIRE + MQC.MQOO_BROWSE
					+ MQC.MQOO_FAIL_IF_QUIESCING + MQC.MQOO_INPUT_SHARED;
			MQQueue queue = qMgr.accessQueue(qn, openOptions);
			int depth = queue.getCurrentDepth();
			if (depth == 0) {
				errString = "no messages to browse...";
				return false;
			}

			// Set the get message options...
			MQGetMessageOptions gmo = new MQGetMessageOptions();
			gmo.options = MQC.MQGMO_BROWSE_NEXT + MQC.MQGMO_NO_WAIT
					+ MQC.MQGMO_FAIL_IF_QUIESCING;

			String message = "";
			while (true) {
				MQMessage retrievedMessage = new MQMessage();
				try {
					queue.get(retrievedMessage, gmo);
					retrievedMessage.characterSet = ((Integer) properties
							.get("mqcharset")).intValue();
					int length = retrievedMessage.getMessageLength();
					message = retrievedMessage.readString(length
							/ ((Integer) properties.get("mqcharlength"))
									.intValue());
					msgList.add(message);
					retrievedMessage.clearMessage();
				} catch (IOException ex) {
					errString = "IOException during GET: " + ex.getMessage();
					status = false;
					break;
				} catch (MQException ex) {
					if (!(ex.completionCode == 2 && ex.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE)) {
						errString = "GET Exception: " + ex;
						status = false;
					}
					break;
				}
			}// end of while
			queue.close();
			qMgr.disconnect();
		}
		// If an error has occurred in the above,try to identify what went wrong
		catch (MQException ex) {
			errString = "An MQSeries error occurred :Completion code "
					+ ex.completionCode + " Reason code " + ex.reasonCode;
			status = false;
		}

		return status;
	}

	public boolean clearMessages(String qm, String qn, Hashtable properties)
			throws MQException, IOException {
		boolean status = true;

		MQQueueManager qMgr = new MQQueueManager(qm, properties);
		int openOptions = MQC.MQOO_INQUIRE + MQC.MQOO_FAIL_IF_QUIESCING
				+ MQC.MQOO_INPUT_SHARED;
		MQQueue queue = qMgr.accessQueue(qn, openOptions);
		int depth = queue.getCurrentDepth();
		if (depth == 0) {
			errString = "no messages to clear...";
			return false;
		}

		// Set the get message options...
		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = MQC.MQGMO_NO_WAIT + MQC.MQGMO_FAIL_IF_QUIESCING
				+ MQC.MQGMO_ACCEPT_TRUNCATED_MSG;

		while (true) {
			MQMessage retrievedMessage = new MQMessage();

			try {
				queue.get(retrievedMessage, gmo);
			} catch (MQException ex) {
				if (!(ex.completionCode == 2 && ex.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE)) {
					break;
				}
				throw ex;
			}
			retrievedMessage.clearMessage();

		}// end of while
		queue.close();
		qMgr.disconnect();

		return status;
	}

	public void putMessages(QManagerData qmData, String qName,
			MessageData mensaje) throws MQException, IOException {

		MQEnvironment.hostname = qmData.getHost();
		MQEnvironment.channel = qmData.getChannel().toUpperCase();
		MQEnvironment.port = qmData.getPort();

		MQQueueManager qMgr = new MQQueueManager(qmData.getName());
		int openOptions = MQC.MQOO_FAIL_IF_QUIESCING + MQC.MQOO_OUTPUT;

		// Now specify the queue that we wish to open, and the open
		// options...
		MQQueue queue = qMgr.accessQueue(qName, openOptions);
		MQMessage mqmsg = new MQMessage();
		mqmsg.characterSet = mensaje.getCharSetID();
		mqmsg.writeString(mensaje.getDatos());
		mqmsg.replyToQueueManagerName = mensaje.getReplyToQManager();
		mqmsg.replyToQueueName = mensaje.getReplyToQ();
		System.out.println("Expiry: " + mensaje.getCaducidad());
		if (mensaje.getCaducidad() != 0)
			mqmsg.expiry = (int) mensaje.getCaducidad();
		mqmsg.encoding = mensaje.getCodeCharSet();
		mqmsg.correlationId = toByteArray(mensaje.getCorrID());
		mqmsg.format = mensaje.getFormato();
		mqmsg.groupId = toByteArray(mensaje.getGroupID());
		mqmsg.messageId = toByteArray(mensaje.getMessID());
		// Set the get message options...
		MQPutMessageOptions pmo = new MQPutMessageOptions();

		queue.put(mqmsg, pmo);
		// Close the queue...
		queue.close();
		// Disconnect from the queue manager
		qMgr.disconnect();
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

	// TODO de aqui a abajo hay que implementar
	public QManagerData listQueuesNames(QManagerData qmData)
			throws MQException, IOException {
		PCFAgent agent = new PCFAgent(qmData.getHost(), qmData.getPort(),
				qmData.getChannel());
		MQMessage[] responses;
		List<QueueData> qDataList = new ArrayList<QueueData>();
		QueueData qData;
		PCFParameter[] parameters = { new MQCFST(CMQC.MQCA_Q_NAME, "*"),
				new MQCFIN(CMQC.MQIA_Q_TYPE, MQC.MQQT_LOCAL) };

		MQCFH cfh;
		MQCFSL cfsl;
		responses = agent.send(CMQCFC.MQCMD_INQUIRE_Q_NAMES, parameters);
		cfh = new MQCFH(responses[0]);
		if (cfh.reason == 0) {
			cfsl = new MQCFSL(responses[0]);
			for (int i = 0; i < cfsl.strings.length; i++) {
				qData = new QueueData();
				qData.setName(cfsl.strings[i]);
				System.out.println("Queue: " + cfsl.strings[i]);
				qDataList.add(qData);
			}
			qmData.setQueues(qDataList);
		}
		agent.disconnect();

		return qmData;
	}

	public List<MessageData> listMessage(QManagerData qmData, String qName) {
		// TODO

		return null;
	}

	public QManagerData listQueuesNames(String qmName) throws MQException,
			IOException {
		PCFAgent agent;

		PCFParameter[] parameters = { new MQCFST(CMQC.MQCA_Q_NAME, "*"),
				new MQCFST(CMQC.MQIA_MSG_ENQ_COUNT, "*") };
		MQMessage[] responses;
		MQCFH cfh;
		PCFParameter p;
		String name = null;
		Integer depth = null;
		// Connect a PCFAgent to the specified queue manager
		agent = new PCFAgent(qmName);
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
					p = PCFParameter.nextParameter(responses[i]);
					// Extract what we want from the returned attributes
					int parameter = p.getParameter();
					if (parameter == CMQC.MQIA_MSG_ENQ_COUNT) {
						Integer enq_count = (Integer) p.getValue();
						System.out.println("!!!!!!! MQIA_MSG_ENQ_COUNT = "
								+ enq_count);
					}
				}
			} else {
				System.out.println("PCF error:\n" + cfh);
				// Walk through the returned parameters describing the error
				for (int j = 0; j < cfh.parameterCount; j++) {
					System.out
							.println(PCFParameter.nextParameter(responses[0]));
				}
			}
		}
		// Disconnect
		System.out.print("Disconnecting... ");
		agent.disconnect();
		System.out.println("Done.");
		// TODO retornar objeto
		return null;
	}

	public List<QManagerData> showConfiguredQM() throws FileNotFoundException,
			IOException {
		qmsData = fUtil.readProperties();
		return qmsData;
	}

	public QManagerData getQMConnected(String qmName)
			throws FileNotFoundException, IOException, MQException {
		QManagerData qmData = fUtil.readSelectedQM(qmName);
		DefaultMutableTreeNode qmgr = new DefaultMutableTreeNode(qmName);
		DefaultTreeModel model = new DefaultTreeModel(qmgr);
		qmData = fUtil.readSelectedQM(qmName);
		qmData.setQueues(pfcImpl.listQueueDepth(qmData));
		return qmData;

	}

	public TableModel getMessagesTableModel(QManagerData qmData, String qName)
			throws IOException, MQException {
		List<MessageData> mensajesList = pfcImpl.getMessageList(qmData, qName);

		Object[] mensajesArray = (Object[]) mensajesList.toArray();
		List<Object[]> tableModelList = new ArrayList<Object[]>();
		String[] intermedio;
		String[][] tableModel = new String[mensajesArray.length][6];
		for (int i = 0; i < mensajesArray.length; i++) {
			tableModelList.add(((MessageData) mensajesArray[i]).toArray());
		}
		tableModel = new String[tableModelList.size()][];
		for (int i = 0; i < tableModelList.size(); i++) {
			intermedio = (String[]) tableModelList.get(i);
			tableModel[i] = intermedio;
		}
		TableModel queuesTableModel = new MQTableModel(tableModel,
				new String[] { "Datos", "MessageID", "CorrelID", "ReplyToQ",
						"ReplyToQManager", "Persistencia", "GroupID",
						"Formato", "Encoding", "CharSetID" });

		return queuesTableModel;
	}

	public MessageData execute(MessageData mensaje, QManagerData qmData,
			String colaReq, String colaResp) throws MQException, IOException {
		MessageData response = new MessageData();
		// Setting Environment
		MQEnvironment.hostname = qmData.getHost();
		MQEnvironment.channel = qmData.getChannel().toUpperCase();
		MQEnvironment.port = qmData.getPort();

		MQQueueManager qMgr = new MQQueueManager(qmData.getName());
		int openOptions = MQC.MQOO_FAIL_IF_QUIESCING + MQC.MQOO_OUTPUT;

		// Now specify the queue that we wish to open, and the open
		// options...
		MQQueue queueReq = qMgr.accessQueue(colaReq, openOptions);
		MQMessage mqmsg = new MQMessage();
		mqmsg.characterSet = mensaje.getCharSetID();
		mqmsg.writeString(mensaje.getDatos());
		mqmsg.replyToQueueManagerName = mensaje.getReplyToQManager();
		mqmsg.replyToQueueName = mensaje.getReplyToQ();
		System.out.println("Expiry: " + mensaje.getCaducidad());
		if (mensaje.getCaducidad() != 0)
			mqmsg.expiry = (int) mensaje.getCaducidad();
		mqmsg.encoding = mensaje.getCodeCharSet();
		mqmsg.correlationId = toByteArray(mensaje.getCorrID());
		mqmsg.format = mensaje.getFormato();
		mqmsg.groupId = toByteArray(mensaje.getGroupID());
		mqmsg.messageId = toByteArray(mensaje.getMessID());
		// Set the get message options...
		MQPutMessageOptions pmo = new MQPutMessageOptions();
		queueReq.put(mqmsg, pmo);
		// Close the queue...
		queueReq.close();

		/*************************************************************
		 * 
		 * *
		 ***********************************************************/
		int openOptionsResp = MQC.MQOO_INQUIRE + MQC.MQOO_FAIL_IF_QUIESCING
				+ MQC.MQOO_INPUT_SHARED;
		MQQueue queue = qMgr.accessQueue(colaResp, openOptionsResp);
		int depth = queue.getCurrentDepth();
		if (depth == 0) {
			errString = "no messages to browse...";
			return null;
		}

		// Set the get message options...
		MQGetMessageOptions gmo = new MQGetMessageOptions();
		gmo.options = MQC.MQGMO_NO_WAIT + MQC.MQGMO_FAIL_IF_QUIESCING;

		String message = "";
		MQMessage retrieveMessage = new MQMessage();
		retrieveMessage.messageId = mqmsg.messageId;
		
		queue.get(retrieveMessage, gmo);
		/*************************************************************
		 *
		 **************************************************************/
		response.setCharSetID(retrieveMessage.characterSet);
		response.setCodeCharSet(retrieveMessage.encoding);
		response.setCorrID(MQPFCImpl.dumpHexId(retrieveMessage.correlationId));
		byte[] buffer = new byte[retrieveMessage.getMessageLength()];
		retrieveMessage.readFully(buffer);
		response.setDatos(new String(buffer));
		response.setFormato(retrieveMessage.format);
		response.setGroupID(MQPFCImpl.dumpHexId(retrieveMessage.groupId));
		response.setMessID(MQPFCImpl.dumpHexId(retrieveMessage.messageId));
		response.setPercistente(String.valueOf(retrieveMessage.persistence));
		response.setReplyToQ(retrieveMessage.replyToQueueName);
		response.setReplyToQManager(retrieveMessage.replyToQueueManagerName);

		queue.close();
		// Disconnect from the queue manager
		qMgr.disconnect();
		// TODO
		return response;
	}
}
