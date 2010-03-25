package cl.continuum.mqtoolkit.model;

import java.util.ArrayList;
import java.util.List;

import cl.continuum.mqtoolkit.model.MQTableModel;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import cl.continuum.mqtoolkit.impl.MQPFCImpl;

public class QManagerData {

	private String host;
	private int port;
	private String channel;
	private String name;
	private List<QueueData> queues;

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the queues
	 */
	public List<QueueData> getQueues() {
		return queues;
	}

	/**
	 * @param queues
	 *            the queues to set
	 */
	public void setQueues(List<QueueData> queues) {
		this.queues = queues;
	}

	public DefaultTreeModel getTreeObject() {
		// TODO
		DefaultMutableTreeNode qmanager = new DefaultMutableTreeNode(name);
		DefaultMutableTreeNode queue;
		DefaultMutableTreeNode tree = new DefaultMutableTreeNode("tree");
		QueueData qData;
		int index = 0;
		for (int i = 0; i < queues.size(); i++) {
			qData = queues.get(i);
			if (!qData.getName().contains("SYSTEM")&& !qData.getName().contains("AMQ")) {
				queue = new DefaultMutableTreeNode(qData.getName());
				// TODO cambiar icono segun tipo de cola
				qmanager.insert(queue, index);
				index++;
			}
		}
		tree.insert(qmanager, 0);
		return new DefaultTreeModel(tree);
	}

	public DefaultMutableTreeNode getTreeQM() {
		// TODO
		DefaultMutableTreeNode qmanager = new DefaultMutableTreeNode(name);

		return qmanager;
	}
	
	public TableModel getQueuesTableModel(boolean sys, boolean local, boolean remote, boolean alias, boolean cluster){
		Object[] queuesArray = (Object[])queues.toArray();
		List<Object[]> tableModelList = new ArrayList<Object[]>();
		String[] intermedio;
		String[][] tableModel = new String[queuesArray.length][6];
		for (int i = 0; i < queuesArray.length; i++) {
			/*System.out.println("Tamaño del array: "+queuesArray.length);
			System.out.println("Iterando i = "+i);
			System.out.println("Queue Name: "+((QueueData)queuesArray[i]).getName());
			System.out.println("QUEUE TYPE: "+((QueueData)queuesArray[i]).getType());*/
			if(((QueueData)queuesArray[i]).getType().equalsIgnoreCase(MQPFCImpl._QTSYS)&& !sys)tableModelList.add(((QueueData)queuesArray[i]).toArray());
			if(((QueueData)queuesArray[i]).getType().equalsIgnoreCase(MQPFCImpl._QTALIAS)&& !alias)tableModelList.add(((QueueData)queuesArray[i]).toArray());
			if(((QueueData)queuesArray[i]).getType().equalsIgnoreCase(MQPFCImpl._QTCLUSTER)&& !cluster)tableModelList.add(((QueueData)queuesArray[i]).toArray());
			if(((QueueData)queuesArray[i]).getType().equalsIgnoreCase(MQPFCImpl._QTLOCAL)&& !local)tableModelList.add(((QueueData)queuesArray[i]).toArray());
			if(((QueueData)queuesArray[i]).getType().equalsIgnoreCase(MQPFCImpl._QTREM)&& !remote)tableModelList.add(((QueueData)queuesArray[i]).toArray());
		}
		tableModel = new String[tableModelList.size()][];
		for (int i = 0; i < tableModelList.size(); i++) {
			intermedio = (String[]) tableModelList.get(i);
			tableModel[i] = intermedio;
		}
		TableModel queuesTableModel = 
			new MQTableModel(
					tableModel,
					new String[] { "Nombre", "Tipo", "Profundidad", "Profundidad máxima", "Largo máximo de mensajes", "Persistencia" });
		
		return queuesTableModel;

	}
	public ComboBoxModel getQueueCBList(){
		Object[] queuesArray = (Object[])queues.toArray();
		List<String> intermedio = new ArrayList<String>();
		for (int i = 0; i < queuesArray.length; i++) {
			System.out.println("Tamaño del array: "+queuesArray.length);
			System.out.println("Iterando i = "+i);
			System.out.println("Queue Name: "+((QueueData)queuesArray[i]).getName());
			System.out.println("QUEUE TYPE: "+((QueueData)queuesArray[i]).getType());
			if(((QueueData)queuesArray[i]).getType().equalsIgnoreCase(MQPFCImpl._QTLOCAL))intermedio.add(((QueueData)queuesArray[i]).getName());
		}
		return new DefaultComboBoxModel(intermedio.toArray());
	}
	
	

}
