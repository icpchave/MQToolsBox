/**
 * 
 */
package cl.continuum.mqtoolkit.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.pcf.CMQC;
import com.ibm.mq.pcf.CMQCFC;
import com.ibm.mq.pcf.MQCFH;
import com.ibm.mq.pcf.MQCFIL;
import com.ibm.mq.pcf.MQCFIN;
import com.ibm.mq.pcf.MQCFSL;
import com.ibm.mq.pcf.MQCFST;
import com.ibm.mq.pcf.PCFAgent;
import com.ibm.mq.pcf.PCFConstants;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;
import com.ibm.mq.pcf.PCFParameter;

import cl.continuum.mq.pfc.samples.ReadPCFMessages;
import cl.continuum.mqtoolkit.model.MessageData;
import cl.continuum.mqtoolkit.model.QManagerData;
import cl.continuum.mqtoolkit.model.QueueData;

/**
 * @author israel
 * 
 */
public class MQPFCImpl {

	public static final String _QTSYS = "Sistema";
	public static final String _QTLOCAL = "Local";
	public static final String _QTREM = "Remota";
	public static final String _QTALIAS = "Alias";
	public static final String _QTCLUSTER = "Cluster";
	public static final String _QTMODEL = "Model";

	// Lista colas activas (que tienen mensajes) y la cantidad de mensajes que
	// tiene cada una
	public void listActivesQueues(QManagerData qmData) throws MQException,
			IOException {
		PCFMessageAgent agent;

		// Client connection (host, port, channel).

		agent = new PCFMessageAgent(qmData.getHost(), qmData.getPort(), qmData
				.getChannel());

		PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q);

		request.addParameter(CMQC.MQCA_Q_NAME, "*");
		request.addParameter(CMQC.MQIA_Q_TYPE, MQC.MQQT_LOCAL);
		request.addFilterParameter(CMQC.MQIA_CURRENT_Q_DEPTH,
				CMQCFC.MQCFOP_GREATER, 0);

		PCFMessage[] responses = agent.send(request);

		for (int i = 0; i < responses.length; i++) {
			PCFMessage response = responses[i];
		}
		agent.disconnect();

	}

	// TODO Lista los atributos del QManager, aun falta traducir los nombres de
	// los atributos.
	public void listQMgrsAttrs(QManagerData qmData) throws MQException,
			IOException {
		int[] attrs = { CMQCFC.MQIACF_ALL };
		PCFAgent agent;
		PCFParameter[] parameters = { new MQCFIL(CMQCFC.MQIACF_Q_MGR_ATTRS,
				attrs) };
		MQMessage[] responses;
		MQCFH cfh;
		PCFParameter p;

		// Connect a PCFAgent to the specified queue manager

		agent = new PCFMessageAgent(qmData.getHost(), qmData.getPort(), qmData
				.getChannel());

		// Use the agent to send the request

		responses = agent.send(CMQCFC.MQCMD_INQUIRE_Q_MGR, parameters);
		cfh = new MQCFH(responses[0]);

		// Check the PCF header (MQCFH) in the first response message

		if (cfh.reason == 0) {

			for (int i = 0; i < cfh.parameterCount; i++) {
				// Walk through the returned attributes

				p = PCFParameter.nextParameter(responses[0]);
			}
		} else {
			System.out.println("PCF error:\n" + cfh);

			// Walk through the returned parameters describing the error

			for (int i = 0; i < cfh.parameterCount; i++) {
				System.out.println(PCFParameter.nextParameter(responses[0]));
			}
		}

		// Disconnect

		agent.disconnect();

	}

	// Lista parámetros de colas
	public List<QueueData> listQueueDepth(QManagerData qmData)
			throws MQException, IOException {
		List<QueueData> colas = new ArrayList<QueueData>();
		QueueData cola;
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

		agent = new PCFMessageAgent(qmData.getHost(), qmData.getPort(), qmData
				.getChannel());

		// Use the agent to send the request

		responses = agent.send(CMQCFC.MQCMD_INQUIRE_Q, parameters);

		for (int i = 0; i < responses.length; i++) {
			cfh = new MQCFH(responses[i]);

			cola = new QueueData();
			// Check the PCF header (MQCFH) in the response message

			if (cfh.reason == 0) {
				for (int j = 0; j < cfh.parameterCount; j++) {
					// Extract what we want from the returned attributes

					p = PCFParameter.nextParameter(responses[i]);

					switch (p.getParameter()) {
					case CMQC.MQCA_Q_NAME:
						cola.setName((String) p.getValue());
						break;
					case CMQC.MQIA_CURRENT_Q_DEPTH:
						cola.setDeep((Integer) p.getValue());
						break;
					case CMQC.MQCA_BACKOUT_REQ_Q_NAME:
						reqBacoutQName = (String) p.getValue();
						break;
					case CMQC.MQIA_Q_TYPE:
						qTypeN = ((Integer) p.getValue()).intValue();
						if (CMQC.MQQT_LOCAL == qTypeN)
							if (cola.getName() != null
									&& (cola.getName().contains("SYSTEM") || cola
											.getName().contains("AMQ"))) {
								cola.setType(MQPFCImpl._QTSYS);
							} else {
								cola.setType(MQPFCImpl._QTLOCAL);
							}

						if (CMQC.MQQT_ALIAS == qTypeN)
							cola.setType(MQPFCImpl._QTALIAS);
						if (CMQC.MQQT_CLUSTER == qTypeN)
							cola.setType(MQPFCImpl._QTCLUSTER);
						if (CMQC.MQQT_REMOTE == qTypeN)
							cola.setType(MQPFCImpl._QTREM);
						if (CMQC.MQQT_MODEL == qTypeN)
							cola.setType(MQPFCImpl._QTSYS);
						break;
					case CMQC.MQIA_MAX_MSG_LENGTH:
						cola.setMaxMsgLength((Integer) p.getValue());
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

			} else {
				throw new MQException(cfh.compCode, cfh.reason, cfh);

			}
			colas.add(cola);
		}
		// Disconnect
		agent.disconnect();
		return colas;
	}

	public void listQueueNames(QManagerData qmData) throws MQException,
			IOException {
		PCFAgent agent;
		PCFParameter[] parameters = { new MQCFST(CMQC.MQCA_Q_NAME, "*"),
				new MQCFIN(CMQC.MQIA_Q_TYPE, CMQC.MQQT_ALL) };
		MQMessage[] responses;
		MQCFH cfh;
		MQCFSL cfsl;
		agent = new PCFMessageAgent(qmData.getHost(), qmData.getPort(), qmData
				.getChannel());

		// Use the agent to send the request

		responses = agent.send(CMQCFC.MQCMD_INQUIRE_Q_NAMES, parameters);
		cfh = new MQCFH(responses[0]);

		// Check the PCF header (MQCFH) in the first response message

		if (cfh.reason == 0) {
			cfsl = new MQCFSL(responses[0]);

			for (int i = 0; i < cfsl.strings.length; i++) {
				System.out.println("\t" + cfsl.strings[i]);
			}
		} else {
			System.out.println(cfh);

			// Walk through the returned parameters describing the error

			for (int i = 0; i < cfh.parameterCount; i++) {
				System.out.println(PCFParameter.nextParameter(responses[0]));
			}
		}

		// Disconnect

		System.out.print("Disconnecting... ");
		agent.disconnect();
		System.out.println("Done.");
	}

	public List<MessageData> getMessageList(QManagerData qmData, String qName) throws  IOException, MQException {
		int messageCount = 0;
		MessageData mData;
		List<MessageData> mDataList = new ArrayList<MessageData>();

		MQEnvironment.hostname = qmData.getHost();
		MQEnvironment.channel = qmData.getChannel().toUpperCase();
		MQEnvironment.port = qmData.getPort();
		MQQueueManager qm = new MQQueueManager(qmData.getName());
		MQQueue queue = qm.accessQueue(qName, MQC.MQOO_BROWSE
				| MQC.MQOO_FAIL_IF_QUIESCING);
		MQMessage message ;
		MQGetMessageOptions gmo = new MQGetMessageOptions();

		gmo.options = MQC.MQGMO_BROWSE_NEXT | MQC.MQGMO_NO_WAIT
				| MQC.MQGMO_CONVERT;
		int i = 0;
		while (true) {
			message = new MQMessage();
			i++;
			try {
				queue.get(message, gmo);
			} catch (MQException e) {
				
				if (e.reasonCode == 2033) {
					queue.close();
					qm.disconnect();
					break;
				}else {
					throw e;
				}
			}
			mData = new MessageData();
			
			mData.setCharSetID(message.characterSet);
			mData.setCodeCharSet(message.encoding);
			mData.setCorrID(dumpHexId(message.correlationId));
			byte[] buffer = new byte[message.getMessageLength()];
			message.readFully(buffer);
			mData.setDatos(new String(buffer));
			mData.setFormato(message.format);
			mData.setGroupID(dumpHexId(message.groupId));
			mData.setMessID(dumpHexId(message.messageId));
			mData.setPercistente(String.valueOf(message.persistence));
			mData.setReplyToQ(message.replyToQueueName);
			mData.setReplyToQManager(message.replyToQueueManagerName);
			mDataList.add(mData);
		}
		queue.close();
		qm.disconnect();
		return mDataList;
		
	}
	public static String dumpHexId(byte[] id) {
		String idStr = "";
		for (int i = 0; i < id.length; i++) {
			char b = (char) (id[i] & 0xFF);
			if (b < 0x10) {
				System.out.print("0");
				idStr = idStr+("0");
			}
			idStr = idStr+((String) (Integer.toHexString(b)).toUpperCase());
		}
		return idStr;
	}

}
