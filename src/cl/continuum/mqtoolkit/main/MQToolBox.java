package cl.continuum.mqtoolkit.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import cl.continuum.mqtoolkit.model.MQTableModel;
import cl.continuum.mqtoolkit.model.MessageData;

import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import cl.continuum.mqtoolkit.impl.GetterThread;
import cl.continuum.mqtoolkit.impl.MQPFCImpl;
import cl.continuum.mqtoolkit.impl.SenderThread;
import cl.continuum.mqtoolkit.impl.ToolsImpl;
import cl.continuum.mqtoolkit.model.QManagerData;
import cl.continuum.mqtoolkit.util.FileUtil;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class MQToolBox extends javax.swing.JFrame implements ActionListener,
		ChangeListener, TreeSelectionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7632172994892919442L;
	private JPanel qmExplorer;
	private JPanel vistas;
	private JTabbedPane vistasTabbed;
	private JComboBox recvQueueCB;
	private JButton sendMessageB;
	private JTextArea senMessageBody;
	private JScrollPane sendMessEP;
	private JComboBox sendQueueCB;
	private JPanel recMessagePanel;
	private JPanel senMessagePanel;
	private JScrollPane loadResultsSP;
	private JTextField caducidadText;
	private JLabel caducidadLabel;
	private JComboBox persistenciaCB;
	private JLabel formatLabel;
	private JLabel maxWLabel;
	private JButton backToQList;
	private JButton sendMessB;
	private JButton refreshTable;
	private JTable queuesTable;
	private JScrollPane queuesTableSP;
	private JToggleButton clusterQueueTB;
	private JToggleButton aliasQueueTB;
	private JToggleButton remoteQueue;
	private JToggleButton localQueueTB;
	private JLabel objectDescriptorLabel;
	private JButton emptyQueue;
	private JToggleButton systemQueueOnOf;
	private JPanel toolsButtons;
	private JPanel queueTablePanel;
	private JPanel viewToolsButtons;
	private JComboBox qmgrsCB;
	private JButton refreshQMTree;
	private JButton advanceMQMDB;
	private JTextArea loadMsgBodyText;
	private JScrollPane messageBodySP;
	private JLabel continuumLabel;
	private JLabel recQMLabel;
	private JLabel sendQMLabel;
	private JPanel toolsPanel;
	private JButton cancelMessPropertiesB;
	private JButton applyMessPropertiesB;
	private JLabel messPropertiesLabel;
	private JTextField codificacionText;
	private JLabel codificacionLabel;
	private JTextField charsetIdText;
	private JLabel charsetIDLabel;
	private JTextField formatText;
	private JTextField grupoIDText;
	private JLabel groupIdLabel;
	private JTextField replyToQMText;
	private JLabel replyToQMLabel;
	private JTextField replyToQText;
	private JLabel replyToQLabel;
	private JLabel percistanceLabel;
	private JTextField correlIDText;
	private JLabel correlID;
	private JButton runLoadB;
	public JTable loadResultsTable;
	private JComboBox nMessageCB;
	private JLabel nMessageLabel;
	private JComboBox nCiclosCB;
	private JLabel ciclosLabel;
	private JComboBox respQCB;
	private JLabel endQLabel;
	private JComboBox iniQCB;
	private JLabel iniQLabel;
	private JPanel messagePropertiesPanel;
	private JButton messageProperties;
	private JPanel loadTestConfPanel;
	private JPanel loadTestPanel;
	private JTextField waitMaxText;
	private JTextArea recvMessageBody;
	private JScrollPane recvMessageSP;
	private JPanel functionalTestPanel;
	private JTextArea sendMessage;
	private JComboBox sendQueue;
	private JTree qMTree;
	private JScrollPane qmTreeSP;
	public JTextArea console;
	private JScrollPane consoleSP;
	private JButton disconnect;
	private JButton connect;
	private JButton remQM;
	private JButton addQM;
	public static final String _TYPEQM = "QManager";
	public static final String _TYPEQ = "Cola";
	private QManagerData qmData;
	private String colaSeleccionada;
	List<String> qmNames;
	private MessageData mensaje = new MessageData();
	public TableModel loadResultsTableModel = new MQTableModel(
			new String[][] {}, new String[] { "Ciclo de Requerimiento",
					"Ciclo de Respuesta", "SLA del Ciclo" });

	public boolean isQueue = false;

	// Utilitarios
	ToolsImpl toolsImpl = new ToolsImpl();
	FileUtil fu = new FileUtil();

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager
					.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MQToolBox inst = new MQToolBox();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}

	public MQToolBox() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this
					.setIconImage(new ImageIcon(
							getClass()
									.getClassLoader()
									.getResource(
											"cl/continuum/mqtoolkit/resources/icons/Toolbox-32x32.png"))
							.getImage());
			this.setName("MQToolsBox");
			this.setTitle("Continuum Prototypes MQToolsBox");
			{
				qmExplorer = new JPanel();
				qmExplorer.setPreferredSize(new java.awt.Dimension(939, 662));
				vistas = new JPanel(); //
				getContentPane().add(qmExplorer, BorderLayout.EAST);
				getContentPane().add(vistas, BorderLayout.WEST);
				vistas.setPreferredSize(new java.awt.Dimension(277, 619));//
				{
					vistasTabbed = new JTabbedPane();
					qmExplorer.add(vistasTabbed);
					{
						consoleSP = new JScrollPane();
						qmExplorer.add(consoleSP);
						consoleSP.setPreferredSize(new java.awt.Dimension(882,
								56));
						{
							console = new JTextArea();
							consoleSP.setViewportView(console);
							console
									.setText("Mensajes de Runtime y Excepciones");
						}
					}

					vistasTabbed.setPreferredSize(new java.awt.Dimension(889,
							580));
					{
						toolsPanel = new JPanel();
						ImageIcon toolsIC = new ImageIcon(
								getClass()
										.getClassLoader()
										.getResource(
												"cl/continuum/mqtoolkit/resources/icons/Tools-32.png"));

						vistasTabbed.add(toolsPanel, toolsIC);
						toolsPanel.setPreferredSize(new java.awt.Dimension(884,
								554));
						{
							viewToolsButtons = new JPanel();
							toolsPanel.add(viewToolsButtons);
							viewToolsButtons
									.setPreferredSize(new java.awt.Dimension(
											858, 40));
							viewToolsButtons.setBorder(BorderFactory
									.createBevelBorder(BevelBorder.LOWERED));
							{
								objectDescriptorLabel = new JLabel();
								viewToolsButtons.add(objectDescriptorLabel);
								objectDescriptorLabel
										.setIcon(new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/CAT-Tools-32x32.png")));
								objectDescriptorLabel
										.setPreferredSize(new java.awt.Dimension(
												591, 27));
							}
							{
								systemQueueOnOf = new JToggleButton();
								systemQueueOnOf
										.setToolTipText("Mostrar/Ocultar colas del sistema");
								systemQueueOnOf.addChangeListener(this);
								viewToolsButtons.add(systemQueueOnOf);
								systemQueueOnOf
										.setIcon(new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/cola_sistema.png")));
								systemQueueOnOf.setAlignmentY(0.1f);
							}
							{
								localQueueTB = new JToggleButton();
								localQueueTB.addChangeListener(this);
								localQueueTB
										.setToolTipText("Mostrar/Ocultar colas locales");
								viewToolsButtons.add(localQueueTB);
								localQueueTB
										.setIcon(new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/cola_local.png")));
							}
							{
								remoteQueue = new JToggleButton();
								remoteQueue.addChangeListener(this);
								remoteQueue
										.setToolTipText("Mostrar/Ocultar colas remotas");
								viewToolsButtons.add(remoteQueue);
								remoteQueue
										.setIcon(new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/cola_remota.png")));
							}
							{
								aliasQueueTB = new JToggleButton();
								aliasQueueTB.addChangeListener(this);
								aliasQueueTB
										.setToolTipText("Mostrar/Ocultar colas alias");
								viewToolsButtons.add(aliasQueueTB);
								aliasQueueTB
										.setIcon(new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/cola_alias.png")));
							}
							{
								clusterQueueTB = new JToggleButton();
								clusterQueueTB.addChangeListener(this);
								clusterQueueTB
										.setToolTipText("Mostrar/Ocultar colas de cluster");
								viewToolsButtons.add(clusterQueueTB);
								clusterQueueTB
										.setIcon(new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/cola_transmision.png")));
							}
							{
								refreshTable = new JButton();
								refreshTable.addActionListener(this);
								refreshTable.setToolTipText("Refrescar");
								viewToolsButtons.add(refreshTable);
								refreshTable
										.setIcon(new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/Gnome-View-Refresh-16.png")));
							}
							{
								backToQList = new JButton();
								backToQList.addActionListener(this);
								backToQList.setEnabled(false);
								backToQList.setToolTipText("Regresar");
								viewToolsButtons.add(backToQList);
								backToQList
										.setIcon(new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/back16.png")));
							}
						}
						{
							queueTablePanel = new JPanel();
							toolsPanel.add(queueTablePanel);
							queueTablePanel
									.setPreferredSize(new java.awt.Dimension(
											863, 455));
							queueTablePanel.setBorder(BorderFactory
									.createBevelBorder(BevelBorder.LOWERED));
							{
								queuesTableSP = new JScrollPane();
								queueTablePanel.add(queuesTableSP);
								queuesTableSP
										.setPreferredSize(new java.awt.Dimension(
												855, 448));
								queuesTableSP.setAlignmentY(0.0f);
								queuesTableSP.setAlignmentX(0.0f);
								{

									queuesTable = new JTable();
									queuesTable.setAutoscrolls(true);
									queuesTable
											.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
									queuesTable.setMinimumSize(new Dimension(
											850, 444));
									queuesTable.setMaximumSize(new Dimension(
											850, 4000));
									queuesTable
											.getSelectionModel()
											.addListSelectionListener(
													new ListSelectionListener() {
														public void valueChanged(
																ListSelectionEvent e) {
															TableModel model = queuesTable
																	.getModel();
															System.out
																	.println("Selected row:"
																			+ queuesTable
																					.getSelectedRow());
															if (queuesTable
																	.getSelectedRow() != -1) {
																if (((String) model
																		.getValueAt(
																				queuesTable
																						.getSelectedRow(),
																				0)) != null
																		&& !((String) model
																				.getValueAt(
																						queuesTable
																								.getSelectedRow(),
																						0))
																				.isEmpty()) {
																	colaSeleccionada = (String) model
																			.getValueAt(
																					queuesTable
																							.getSelectedRow(),
																					0);
																	System.out
																			.println("Cola Seleccionada: "
																					+ colaSeleccionada);
																}

															}
														}
													});
									queuesTable.setAutoCreateRowSorter(true);
									queuesTableSP.setViewportView(queuesTable);
									queuesTable.setSize(850, 444);
									queuesTable.setVisible(false);
									queuesTable
											.addMouseListener(new MouseAdapter() {
												public void mouseClicked(
														MouseEvent e) {
													if (e.getClickCount() == 2) {
														if (isQueue) {
															setTableMessages();
														} else {
															// TODO Crear un
															// dialogo con los
															// datos del mensaje
														}
													}
												}
											});
								}
							}
						}
						{
							toolsButtons = new JPanel();
							toolsPanel.add(toolsButtons);
							toolsButtons
									.setPreferredSize(new java.awt.Dimension(
											863, 36));
							toolsButtons.setBorder(BorderFactory
									.createBevelBorder(BevelBorder.LOWERED));
							{
								sendMessB = new JButton();
								toolsButtons.add(sendMessB);
								sendMessB.setText("Enviar Mensaje");
								sendMessB.addActionListener(this);
								sendMessB.setVisible(false);
							}
							{
								emptyQueue = new JButton();
								toolsButtons.add(emptyQueue);
								emptyQueue.setText("Vaciar Cola");
								emptyQueue.addActionListener(this);
								emptyQueue.setVisible(false);
							}
						}
					}
					{
						functionalTestPanel = new JPanel();
						vistasTabbed
								.add(
										functionalTestPanel,
										new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/shotguns_32.png")));
						functionalTestPanel
								.setPreferredSize(new java.awt.Dimension(884,
										535));
						{
							senMessagePanel = new JPanel();
							functionalTestPanel.add(senMessagePanel);
							senMessagePanel
									.setPreferredSize(new java.awt.Dimension(
											421, 546));
							{
								sendQMLabel = new JLabel();
								senMessagePanel.add(sendQMLabel);
								sendQMLabel
										.setText("Seleccione la cola de requerimientos");
								sendQMLabel
										.setPreferredSize(new java.awt.Dimension(
												183, 14));
							}
							{
								ComboBoxModel sendQueueCBModel = new DefaultComboBoxModel();
								if (qmData != null)
									sendQueueCBModel = qmData.getQueueCBList();
								sendQueueCBModel
										.setSelectedItem("Seleccione la cola de requerimientos");
								sendQueueCB = new JComboBox();
								senMessagePanel.add(sendQueueCB);
								sendQueueCB.setModel(sendQueueCBModel);
								sendQueueCB
										.setPreferredSize(new java.awt.Dimension(
												222, 21));
							}
							{
								sendMessEP = new JScrollPane();
								senMessagePanel.add(sendMessEP);
								sendMessEP
										.setPreferredSize(new java.awt.Dimension(
												427, 470));
								{
									senMessageBody = new JTextArea();
									sendMessEP.setViewportView(senMessageBody);
									senMessageBody
											.setText("Escriba aqui el mensaje a enviar");
									senMessageBody
											.setPreferredSize(new java.awt.Dimension(
													424, 447));
								}
							}
							{
								messageProperties = new JButton(
										new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/Configuration-Settings-16.png")));
								senMessagePanel.add(messageProperties);
								messageProperties.addActionListener(this);
								messageProperties
										.setText("Propiedades del Mensaje");
								messageProperties
										.setPreferredSize(new java.awt.Dimension(
												161, 21));
							}
							{
								sendMessageB = new JButton(
										new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/Play1Hot_16.png")));
								senMessagePanel.add(sendMessageB);
								sendMessageB.setText("Enviar");
								sendMessageB
										.setPreferredSize(new java.awt.Dimension(
												98, 21));
								sendMessageB.addActionListener(this);
							}
						}
						{
							recMessagePanel = new JPanel();
							functionalTestPanel.add(recMessagePanel);
							recMessagePanel
									.setPreferredSize(new java.awt.Dimension(
											421, 543));
							{
								recQMLabel = new JLabel();
								recMessagePanel.add(recQMLabel);
								recQMLabel
										.setText("Seleccione la cola de respuesta");
								recQMLabel
										.setPreferredSize(new java.awt.Dimension(
												183, 14));
							}
							{
								ComboBoxModel recvQueueCBModel = new DefaultComboBoxModel();
								if (qmData != null)
									recvQueueCBModel = qmData.getQueueCBList();
								recvQueueCBModel
										.setSelectedItem("Seleccione la cola de respuestas");
								recvQueueCB = new JComboBox();
								recMessagePanel.add(recvQueueCB);
								recvQueueCB.setModel(recvQueueCBModel);
								recvQueueCB
										.setPreferredSize(new java.awt.Dimension(
												222, 21));
							}
							{
								recvMessageSP = new JScrollPane();
								recMessagePanel.add(recvMessageSP);
								recvMessageSP
										.setPreferredSize(new java.awt.Dimension(
												427, 503));
								{
									recvMessageBody = new JTextArea();
									recvMessageSP
											.setViewportView(recvMessageBody);
									recvMessageBody
											.setText("El mensaje de respuesta sera mostrado aqui");
									recvMessageBody
											.setPreferredSize(new java.awt.Dimension(
													424, 499));
								}
							}
						}
					}
					{
						loadTestPanel = new JPanel();
						vistasTabbed
								.add(
										loadTestPanel,
										new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/ak47_32.png")));
						loadTestPanel.setPreferredSize(new java.awt.Dimension(
								884, 535));
						loadTestPanel.setVisible(false);
						{
							loadTestConfPanel = new JPanel();
							loadTestConfPanel.setBorder(BorderFactory
									.createBevelBorder(BevelBorder.LOWERED,
											new java.awt.Color(192, 192, 192),
											null, null, new java.awt.Color(192,
													192, 192)));
							loadTestPanel.add(loadTestConfPanel);
							loadTestConfPanel
									.setPreferredSize(new java.awt.Dimension(
											489, 539));
							{
								iniQLabel = new JLabel();
								loadTestConfPanel.add(iniQLabel);
								iniQLabel
										.setText("Seleccione Cola de Requerimientos");
								iniQLabel
										.setPreferredSize(new java.awt.Dimension(
												185, 14));
							}
							{
								ComboBoxModel iniQCBModel = new DefaultComboBoxModel(
										new String[] { "Cola de requerimientos" });
								iniQCB = new JComboBox();
								loadTestConfPanel.add(iniQCB);
								iniQCB.setModel(iniQCBModel);
								iniQCB.setPreferredSize(new java.awt.Dimension(
										233, 21));
							}
							{
								endQLabel = new JLabel();
								loadTestConfPanel.add(endQLabel);
								endQLabel
										.setText("Seleccione Cola de Respuestas");
								endQLabel
										.setPreferredSize(new java.awt.Dimension(
												185, 14));
							}
							{
								ComboBoxModel respQCBModel = new DefaultComboBoxModel(
										new String[] { "Cola de respuesta" });
								respQCB = new JComboBox();
								loadTestConfPanel.add(respQCB);
								respQCB.setModel(respQCBModel);
								respQCB
										.setPreferredSize(new java.awt.Dimension(
												233, 21));
							}
							{
								ciclosLabel = new JLabel();
								loadTestConfPanel.add(ciclosLabel);
								ciclosLabel
										.setText("Seleccione la cantidad de ciclos del Test");
								ciclosLabel.setPreferredSize(new java.awt.Dimension(265, 14));
							}
							{
								ComboBoxModel nCiclosCBModel = new DefaultComboBoxModel(
										new String[] { "1", "2", "3", "4", "5",
												"6", "7", "8", "9", "10", "15",
												"20", "30" });
								nCiclosCB = new JComboBox();
								loadTestConfPanel.add(nCiclosCB);
								nCiclosCB.setModel(nCiclosCBModel);
								nCiclosCB.setPreferredSize(new java.awt.Dimension(147, 21));
								nCiclosCB.setEditable(true);
							}
							{
								nMessageLabel = new JLabel();
								loadTestConfPanel.add(nMessageLabel);
								nMessageLabel
										.setText("Seleccione la cantidad de mensajes por ciclo");
								nMessageLabel.setPreferredSize(new java.awt.Dimension(265, 14));
							}
							{
								ComboBoxModel nMessageCBModel = new DefaultComboBoxModel(
										new String[] { "10", "30", "50", "100",
												"300", "500", "1000", "3000",
												"5000", "10000", "20000",
												"30000" });
								nMessageCB = new JComboBox();
								loadTestConfPanel.add(nMessageCB);
								nMessageCB.setModel(nMessageCBModel);
								nMessageCB.setPreferredSize(new java.awt.Dimension(147, 21));
								nMessageCB.setEditable(true);
							}
							{
								maxWLabel = new JLabel();
								loadTestConfPanel.add(maxWLabel);
								maxWLabel.setText("Tiempo Máximo de Espera");
								maxWLabel.setPreferredSize(new java.awt.Dimension(312, 14));
							}
							{
								waitMaxText = new JTextField();
								loadTestConfPanel.add(waitMaxText);
								waitMaxText.setColumns(5);
								waitMaxText.setText("15000");
								waitMaxText.setPreferredSize(new java.awt.Dimension(100, 21));
							}
							{
								loadResultsSP = new JScrollPane();
								loadTestConfPanel.add(loadResultsSP);
								loadResultsSP.setPreferredSize(new java.awt.Dimension(460, 360));
								{
									/*
									 * TableModel loadResultsTableModel = new
									 * MQTableModel( new String[][] { { "One",
									 * "Two" }, { "Three", "Four" }, { "Three",
									 * "Four" }, { "Three", "Four" } }, new
									 * String[] { "Ciclo de Requerimiento",
									 * "Ciclo de Respuesta", "SLA del Ciclo" });
									 */

									loadResultsTable = new JTable();
									loadResultsTable
											.setAutoCreateRowSorter(true);
									loadResultsSP
											.setViewportView(loadResultsTable);
									loadResultsTable
											.setModel(loadResultsTableModel);
								}
							}
							{
								runLoadB = new JButton(
										new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/Play1Hot_16.png")));
								loadTestConfPanel.add(runLoadB);
								runLoadB.setText("Ejecutar Test");
								runLoadB.addActionListener(this);
							}
						}
						{
							messagePropertiesPanel = new JPanel();
							loadTestPanel.add(messagePropertiesPanel);
							messagePropertiesPanel.setBorder(BorderFactory
									.createBevelBorder(BevelBorder.LOWERED,
											new java.awt.Color(192, 192, 192),
											null, null, new java.awt.Color(192,
													192, 192)));
							messagePropertiesPanel
									.setPreferredSize(new java.awt.Dimension(
											380, 539));
							{
								messPropertiesLabel = new JLabel();
								messagePropertiesPanel.add(messPropertiesLabel);
								messPropertiesLabel.setFont(new Font("Dialog",
										Font.BOLD, 18));
								messPropertiesLabel
										.setText("Propiedades del Mensaje");
								messPropertiesLabel
										.setIcon(new ImageIcon(
												getClass()
														.getClassLoader()
														.getResource(
																"cl/continuum/mqtoolkit/resources/icons/E-Mail-Config-64.png")));
								messPropertiesLabel
										.setPreferredSize(new java.awt.Dimension(
												311, 64));
							}
							{
								correlID = new JLabel();
								messagePropertiesPanel.add(correlID);
								correlID.setText("Correlational ID");
								correlID
										.setPreferredSize(new java.awt.Dimension(
												231, 14));
							}
							{
								correlIDText = new JTextField();
								messagePropertiesPanel.add(correlIDText);
								correlIDText
										.setPreferredSize(new java.awt.Dimension(
												126, 21));
							}
							{
								groupIdLabel = new JLabel();
								messagePropertiesPanel.add(groupIdLabel);
								groupIdLabel.setText("Identidad de grupo");
								groupIdLabel
										.setPreferredSize(new java.awt.Dimension(
												231, 14));
							}
							{
								grupoIDText = new JTextField();
								messagePropertiesPanel.add(grupoIDText);
								grupoIDText
										.setPreferredSize(new java.awt.Dimension(
												126, 21));
							}
							{
								percistanceLabel = new JLabel();
								messagePropertiesPanel.add(percistanceLabel);
								percistanceLabel.setText("Percistencia");
								percistanceLabel
										.setPreferredSize(new java.awt.Dimension(
												231, 14));
							}
							{
								ComboBoxModel persistenciaCBModel = new DefaultComboBoxModel(
										new String[] { "Si", "No", "Según QDef" });
								persistenciaCB = new JComboBox();
								messagePropertiesPanel.add(persistenciaCB);
								persistenciaCB.setModel(persistenciaCBModel);
								persistenciaCB
										.setPreferredSize(new java.awt.Dimension(
												126, 21));
							}
							{
								caducidadLabel = new JLabel();
								messagePropertiesPanel.add(caducidadLabel);
								caducidadLabel.setText("Caducidad");
								caducidadLabel
										.setPreferredSize(new java.awt.Dimension(
												231, 14));
							}
							{
								caducidadText = new JTextField();
								messagePropertiesPanel.add(caducidadText);
								caducidadText.setText("00000");
								caducidadText
										.setPreferredSize(new java.awt.Dimension(
												126, 21));
							}
							{
								replyToQLabel = new JLabel();
								messagePropertiesPanel.add(replyToQLabel);
								replyToQLabel.setText("Cola de respuesta");
								replyToQLabel
										.setPreferredSize(new java.awt.Dimension(
												231, 14));
							}
							{
								replyToQText = new JTextField();
								messagePropertiesPanel.add(replyToQText);
								replyToQText
										.setPreferredSize(new java.awt.Dimension(
												126, 21));
							}
							{
								replyToQMLabel = new JLabel();
								messagePropertiesPanel.add(replyToQMLabel);
								replyToQMLabel.setText("QManager de respuesta");
								replyToQMLabel
										.setPreferredSize(new java.awt.Dimension(
												231, 14));
							}
							{
								replyToQMText = new JTextField();
								messagePropertiesPanel.add(replyToQMText);
								replyToQMText
										.setPreferredSize(new java.awt.Dimension(
												126, 21));
							}
							{
								formatLabel = new JLabel();
								messagePropertiesPanel.add(formatLabel);
								formatLabel.setText("Formato");
								formatLabel
										.setPreferredSize(new java.awt.Dimension(
												231, 14));
							}
							{
								formatText = new JTextField();
								messagePropertiesPanel.add(formatText);
								formatText
										.setPreferredSize(new java.awt.Dimension(
												126, 21));
								formatText.setText("MQSTR");
							}
							{
								charsetIDLabel = new JLabel();
								messagePropertiesPanel.add(charsetIDLabel);
								charsetIDLabel
										.setText("Identificador de juego de caracteres codificado");
								charsetIDLabel
										.setPreferredSize(new java.awt.Dimension(
												231, 14));
							}
							{
								charsetIdText = new JTextField();
								messagePropertiesPanel.add(charsetIdText);
								charsetIdText
										.setPreferredSize(new java.awt.Dimension(
												126, 21));
							}
							{
								codificacionLabel = new JLabel();
								messagePropertiesPanel.add(codificacionLabel);
								codificacionLabel.setText("Codificación");
								codificacionLabel
										.setPreferredSize(new java.awt.Dimension(
												231, 14));
							}
							{
								codificacionText = new JTextField();
								messagePropertiesPanel.add(codificacionText);
								codificacionText
										.setPreferredSize(new java.awt.Dimension(
												126, 21));
							}
							{
								messageBodySP = new JScrollPane();
								messagePropertiesPanel.add(messageBodySP);
								messageBodySP
										.setPreferredSize(new java.awt.Dimension(
												371, 193));
								{
									loadMsgBodyText = new JTextArea();
									messageBodySP
											.setViewportView(loadMsgBodyText);
									loadMsgBodyText
											.setText("Escriba aqui el cuerpo del mensaje, incluya # para correlativos numéricos");
								}
							}
							{
								advanceMQMDB = new JButton();
								messagePropertiesPanel.add(advanceMQMDB);
								advanceMQMDB.setText("Propiedades Avanzadas");
								advanceMQMDB.addActionListener(this);
							}
							{
								cancelMessPropertiesB = new JButton();
								messagePropertiesPanel
										.add(cancelMessPropertiesB);
								cancelMessPropertiesB.setText("Restablecer");
								cancelMessPropertiesB.addActionListener(this);
							}
							{
								applyMessPropertiesB = new JButton();
								applyMessPropertiesB.addActionListener(this);
								messagePropertiesPanel
										.add(applyMessPropertiesB);
								applyMessPropertiesB.setText("Aplicar");
								applyMessPropertiesB
										.setPreferredSize(new java.awt.Dimension(
												74, 21));
							}
						}
					}
				}

				{
					addQM = new JButton(
							new ImageIcon(
									getClass()
											.getClassLoader()
											.getResource(
													"cl/continuum/mqtoolkit/resources/icons/add-server-icon-32.png")));
					addQM.setSize(39, 39);
					addQM.setToolTipText("Adicionar QManager");
					addQM.addActionListener(this);
					vistas.add(addQM);//
				}
				{
					remQM = new JButton(
							new ImageIcon(
									getClass()
											.getClassLoader()
											.getResource(
													"cl/continuum/mqtoolkit/resources/icons/desable-server-icon-32.png")));
					remQM.setSize(39, 39);
					remQM.setToolTipText("Eliminar QManager");
					remQM.addActionListener(this);
					vistas.add(remQM);//
				}
				{
					connect = new JButton();
					connect.addActionListener(this);
					vistas.add(connect);
					{
						disconnect = new JButton();
						disconnect.setToolTipText("Desconectarse del QManager");
						disconnect.addActionListener(this);
						vistas.add(disconnect);
						disconnect.setSize(39, 39);
						disconnect.setOpaque(false);
					}
					connect
							.setIcon(new ImageIcon(
									getClass()
											.getClassLoader()
											.getResource(
													"cl/continuum/mqtoolkit/resources/icons/connect_creating-32.png")));
					connect
							.setToolTipText("Conectarse al QManager Seleccionado");

				}
				{
					/*
					 * disconnect = new JButton();
					 * disconnect.setToolTipText("Desconectarse del QManager");
					 * disconnect.addActionListener(this);
					 */
					{
						refreshQMTree = new JButton();
						refreshQMTree.setToolTipText("Refrescar");
						refreshQMTree.addActionListener(this);
						vistas.add(refreshQMTree);
						refreshQMTree
								.setIcon(new ImageIcon(
										getClass()
												.getClassLoader()
												.getResource(
														"cl/continuum/mqtoolkit/resources/icons/Gnome-View-Refresh-32.png")));
					}
					{
						qmNames = new ArrayList<String>();
						List<QManagerData> qmgrsData = toolsImpl
								.showConfiguredQM();
						for (int i = 0; i < qmgrsData.size(); i++) {
							qmNames.add(qmgrsData.get(i).getName());
						}
						ComboBoxModel qmgrsCBModel = new DefaultComboBoxModel(
								qmNames.toArray());
						qmgrsCBModel.setSelectedItem("Seleccione un QManager");
						qmgrsCB = new JComboBox();
						vistas.add(qmgrsCB);
						qmgrsCB.setModel(qmgrsCBModel);
						qmgrsCB
								.setPreferredSize(new java.awt.Dimension(232,
										21));
					}
					{
						qmTreeSP = new JScrollPane();
						vistas.add(qmTreeSP);
						qmTreeSP.setPreferredSize(new java.awt.Dimension(272,
								510));
						{
							String[] defMess = { "Seleccione un QManager para conectarse" };
							qMTree = new JTree(defMess);
							qMTree.addTreeSelectionListener(this);
							DefaultTreeCellRenderer render = (DefaultTreeCellRenderer) qMTree
									.getCellRenderer();
							render
									.setLeafIcon(new ImageIcon(
											getClass()
													.getClassLoader()
													.getResource(
															"cl/continuum/mqtoolkit/resources/icons/cola_local.png")));
							render
									.setOpenIcon(new ImageIcon(
											getClass()
													.getClassLoader()
													.getResource(
															"cl/continuum/mqtoolkit/resources/icons/server_conectado(2).png")));
							render
									.setClosedIcon(new ImageIcon(
											getClass()
													.getClassLoader()
													.getResource(
															"cl/continuum/mqtoolkit/resources/icons/server_conectado(2).png")));
							qmTreeSP.setViewportView(qMTree);
							qMTree.setPreferredSize(new java.awt.Dimension(269,
									507));
							qMTree.setAutoscrolls(true);
							qMTree.setBorder(BorderFactory.createBevelBorder(
									BevelBorder.LOWERED, new java.awt.Color(
											192, 192, 192), null, null,
									new java.awt.Color(192, 192, 192)));
						}
					}
					{
						continuumLabel = new JLabel();
						vistas.add(continuumLabel);
						continuumLabel
								.setIcon(new ImageIcon(
										getClass()
												.getClassLoader()
												.getResource(
														"cl/continuum/mqtoolkit/resources/icons/logo_app2.png")));
						continuumLabel.setPreferredSize(new java.awt.Dimension(173, 52));
						continuumLabel.addMouseListener(this);
					}
					disconnect
							.setIcon(new ImageIcon(
									getClass()
											.getClassLoader()
											.getResource(
													"cl/continuum/mqtoolkit/resources/icons/connect_no-32.png")));
				}

			}
			pack();
			this.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the qmExplorer
	 */
	public JPanel getQmExplorer() {
		return qmExplorer;
	}

	/**
	 * @param qmExplorer
	 *            the qmExplorer to set
	 */
	public void setQmExplorer(JPanel qmExplorer) {
		this.qmExplorer = qmExplorer;
	}

	/**
	 * @return the vistas
	 */
	public JPanel getVistas() {
		return vistas;
	}

	/**
	 * @param vistas
	 *            the vistas to set
	 */
	public void setVistas(JPanel vistas) {
		this.vistas = vistas;
	}

	/**
	 * @return the vistasTabbed
	 */
	public JTabbedPane getVistasTabbed() {
		return vistasTabbed;
	}

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setMessages(JSplitPane messages) {

	}

	/**
	 * @param console
	 *            the console to set
	 */
	public void setConsole(JTextArea console) {
	}

	/**
	 * @return the diconnect
	 */
	public JButton getDisconnect() {
		return disconnect;
	}

	/**
	 * @param diconnect
	 *            the diconnect to set
	 */
	public void setDisconnect(JButton disconnect) {
	}

	/**
	 * @return the connect
	 */
	public JButton getConnect() {
		return connect;
	}

	/**
	 * @param connect
	 *            the connect to set
	 */
	public void setConnect(JButton connect) {
		this.connect = connect;
		connect.setSize(39, 39);
		connect.setPreferredSize(new java.awt.Dimension(36, 37));
	}

	/**
	 * @return the remQM
	 */
	public JButton getRemQM() {
		return remQM;
	}

	/**
	 * @param remQM
	 *            the remQM to set
	 */
	public void setRemQM(JButton remQM) {
		this.remQM = remQM;
		remQM.setPreferredSize(new java.awt.Dimension(39, 39));
		remQM.setMargin(new java.awt.Insets(1, 1, 1, 1));
		remQM.setMaximumSize(new java.awt.Dimension(7, 7));
		remQM.setMinimumSize(new java.awt.Dimension(7, 7));
	}

	/**
	 * @return the addQM
	 */
	public JButton getAddQM() {
		return addQM;
	}

	/**
	 * @param addQM
	 *            the addQM to set
	 */
	public void setAddQM(JButton addQM) {
		this.addQM = addQM;
		addQM.setPreferredSize(new java.awt.Dimension(39, 39));
	}

	/**
	 * @return the qMTree
	 */
	public JTree getqMTree() {
		return qMTree;
	}

	/**
	 * @param qMTree
	 *            the qMTree to set
	 */
	public void setqMTree(JTree qMTree) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == connect) {

			if (((String) qmgrsCB.getSelectedItem())
					.equalsIgnoreCase("Seleccione un QManager")) {
				JOptionPane.showMessageDialog(new Frame("Advertencia"),
						"Debe seleccionar el QManager al cual conectarse.",
						"Seleccione un QManager.", JOptionPane.ERROR_MESSAGE);

			} else {
				try {
					qmData = toolsImpl.getQMConnected((String) qmgrsCB
							.getSelectedItem());
					qMTree.setModel(qmData.getTreeObject());
					console.setText("Conectado a QManager: "
							+ (String) qmgrsCB.getSelectedItem());
					setTableQueues();
					emptyQueue.setVisible(true);
					sendMessB.setVisible(true);
					refreshQLists();
				} catch (FileNotFoundException e1) {
					console.setText(e1.getMessage());
				} catch (IOException e1) {
					console.setText(e1.getMessage());
				} catch (MQException e1) {
					console.setText("RC: " + e1.reasonCode + " Mensaje:"
							+ e1.getMessage());
				}
			}

		} else if (e.getSource() == disconnect) {
			DefaultMutableTreeNode tree = new DefaultMutableTreeNode("tree");
			DefaultMutableTreeNode mensaje = new DefaultMutableTreeNode(
					"Seleccione un QManager para conectarse");
			tree.insert(mensaje, 0);
			qMTree.setModel(new DefaultTreeModel(tree));
			console.setText("Desconectando.");

		} else if (e.getSource() == addQM) {
			JFrame newQM = new JFrame("Configurar nuevo QManager");
			QMConfigData qmConfig = new QMConfigData(newQM, this);
			qmConfig.initGUI();
			qmConfig.setLocationRelativeTo(newQM);
			qmConfig.pack();
			qmConfig.setVisible(true);
			console.setText("QManager creado.");

		} else if (e.getSource() == remQM) {
			if (((String) qmgrsCB.getSelectedItem())
					.equalsIgnoreCase("Seleccione un QManager")) {
				JOptionPane.showMessageDialog(new Frame("Advertencia"),
						"Debe seleccionar el QManager a borrar.",
						"Seleccione un QManager.", JOptionPane.ERROR_MESSAGE);

			} else {
				try {
					String[] options = { "Eliminar", "Cancelar" };
					int n = JOptionPane.showOptionDialog(new Frame(
							"Advertencia"), "Desea eliminar el QManager: "
							+ (String) qmgrsCB.getSelectedItem(),
							"Eliminando QManager", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, // do not use a
							// custom Icon
							options, // the titles of buttons
							options[0]); // default button title
					if (n == JOptionPane.YES_OPTION) {
						fu.removeQM((String) qmgrsCB.getSelectedItem());
						refreshQMList();
					}
				} catch (FileNotFoundException e1) {
					console.setText(e1.getMessage());
				} catch (IOException e1) {
					console.setText(e1.getMessage());
				}
			}
		} else if (e.getSource() == refreshQMTree) {
			if (((String) qmgrsCB.getSelectedItem())
					.equalsIgnoreCase("Seleccione un QManager")) {
				JOptionPane.showMessageDialog(new Frame("Advertencia"),
						"Usted debe conectarse a un QManager antes",
						"Seleccione un QManager.", JOptionPane.ERROR_MESSAGE);

			} else {
				try {
					// setqMTree(new JTree(toolsImpl.getQMConnected((String)
					// qmgrsCB.getSelectedItem()).getTreeObject()));
					qMTree
							.setModel(toolsImpl.getQMConnected(
									(String) qmgrsCB.getSelectedItem())
									.getTreeObject());
					console.setText("Conectado a QManager: "
							+ (String) qmgrsCB.getSelectedItem());
				} catch (FileNotFoundException e1) {
					console.setText(e1.getMessage());
				} catch (IOException e1) {
					console.setText(e1.getMessage());
				} catch (MQException e1) {
					console.setText("RC: " + e1.reasonCode + " Mensaje:"
							+ e1.getMessage());
				}
			}
		} else if (e.getSource() == refreshTable) {
			if (qmData != null) {
				System.out.println("Capturando evento de boton refreshTable");
				setTableQueues();
			} else {
				JOptionPane.showMessageDialog(new Frame("Advertencia"),
						"Debe conectarse a un QManager.",
						"Conectese a un QManager.", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == emptyQueue) {
			emptyQueue();
		} else if (e.getSource() == sendMessB) {
			if (colaSeleccionada == null || colaSeleccionada.isEmpty()) {
				JOptionPane.showMessageDialog(new Frame("Advertencia"),
						"Debe seleccionar la cola de destino.",
						"Conectese una cola.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Message4SendDialog sendMessageD = new Message4SendDialog(qmData,
					colaSeleccionada, this);
			sendMessageD.setVisible(true);

		} else if (e.getSource() == backToQList) {
			if (qmData != null) {
				System.out.println("Capturando evento de boton backToQList");
				setTableQueues();
			}
		} else if (e.getSource() == messageProperties) {
			MessageMQMD messageMQMD = new MessageMQMD(mensaje);
			messageMQMD.setVisible(true);
		} else if (e.getSource() == sendMessageB) {
			if (qmData == null) {
				JOptionPane.showMessageDialog(new Frame("Advertencia"),
						"Debe conectarse a un QManager.",
						"Conectese a un QManager.", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (((String) sendQueueCB.getSelectedItem())
					.equalsIgnoreCase("Seleccione la cola de requerimientos")
					|| ((String) recvQueueCB.getSelectedItem())
							.equalsIgnoreCase("Seleccione la cola de respuestas")) {
				JOptionPane
						.showMessageDialog(
								new Frame("Advertencia"),
								"Debe seleccionar las colas de requerimiento y respuesta.",
								"Seleccione las colas.",
								JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				if (mensaje == null) {
					mensaje = new MessageData();
				}
				mensaje.setDatos(senMessageBody.getText());
				try {
					mensaje = toolsImpl.execute(mensaje, qmData,
							(String) sendQueueCB.getSelectedItem(),
							(String) recvQueueCB.getSelectedItem());
				} catch (MQException e1) {
					console.setText("RC: " + e1.reasonCode + " Mensaje:"
							+ e1.getMessage());
				} catch (IOException e1) {
					console.setText(e1.getMessage());
				}
				if (mensaje != null) {
					recvMessageBody.setText(mensaje.getDatos());
				} else {
					recvMessageBody
							.setText("No se recibió respuesta del servicio");
				}
			}

		} else if (e.getSource() == runLoadB) {
			if (qmData == null) {
				JOptionPane.showMessageDialog(new Frame("Advertencia"),
						"Debe conectarse a un QManager.",
						"Conectese a un QManager.", JOptionPane.ERROR_MESSAGE);
				return;
			} else if (((String) iniQCB.getSelectedItem())
					.equalsIgnoreCase("Cola de requerimientos")
					|| ((String) respQCB.getSelectedItem())
							.equalsIgnoreCase("Cola de respuesta")) {
				JOptionPane
						.showMessageDialog(
								new Frame("Advertencia"),
								"Debe seleccionar las colas de requerimiento y respuesta.",
								"Seleccione las colas.",
								JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				mensaje = new MessageData();
				mensaje.setCaducidad(Long.parseLong(caducidadText.getText()));
				if(!charsetIdText.getText().isEmpty())mensaje.setCharSetID(Integer.parseInt(charsetIdText.getText()));
				if(!codificacionText.getText().isEmpty())mensaje.setCodeCharSet(Integer.parseInt(codificacionText.getText()));
				if(!correlIDText.getText().isEmpty())mensaje.setCorrID(correlIDText.getText());
				if(!loadMsgBodyText.getText().isEmpty())mensaje.setDatos(loadMsgBodyText.getText());
				if(!formatText.getText().isEmpty())mensaje.setFormato(formatText.getText());
				if(!grupoIDText.getText().isEmpty())mensaje.setGroupID(grupoIDText.getText());
				mensaje.setMessID(MQPFCImpl.dumpHexId(MQC.MQMI_NONE));
				if(!((String)persistenciaCB.getSelectedItem()).isEmpty())mensaje.setPercistente((String)persistenciaCB.getSelectedItem());
				if(!replyToQText.getText().isEmpty())mensaje.setReplyToQ(replyToQText.getText());
				if(!replyToQMText.getText().isEmpty())mensaje.setReplyToQManager(replyToQMText.getText());
				mensaje.setDatos(loadMsgBodyText.getText());
				SenderThread st = new SenderThread(
						(Integer.parseInt((String) nCiclosCB.getSelectedItem())),
						(Integer
								.parseInt((String) nMessageCB.getSelectedItem())),
						this, mensaje, qmData, (String) iniQCB
								.getSelectedItem());
				this.loadResultsTableModel = new MQTableModel(
						new String[12][3], new String[] {
								"Ciclo de Requerimiento", "Ciclo de Respuesta",
								"SLA del Ciclo (ms)" });
				GetterThread gt = new GetterThread((Integer.parseInt((String) nCiclosCB.getSelectedItem())), (Integer
								.parseInt((String) nMessageCB.getSelectedItem())), this, qmData, (String)respQCB.getSelectedItem(), (Integer.parseInt(waitMaxText.getText())));
				st.run();
				gt.run();
				if (isQueue) {
					setTableQueues();
				}else {
					setTableMessages();
				}
			}
		}

	}

	public void refresh(String qmName) {
		// TODO
	}

	public void refreshQMList() throws FileNotFoundException, IOException {
		List<String> qmNames = new ArrayList<String>();
		List<QManagerData> qmgrsData = toolsImpl.showConfiguredQM();
		for (int i = 0; i < qmgrsData.size(); i++) {
			qmNames.add(qmgrsData.get(i).getName());
		}
		ComboBoxModel qmgrsCBModel = new DefaultComboBoxModel(qmNames.toArray());
		qmgrsCBModel.setSelectedItem("Seleccione un QManager");
		qmgrsCB.setModel(qmgrsCBModel);
	}

	private void switchObjetDescriptorLabelText(String objectType,
			String objectName) {
		if (objectType.equalsIgnoreCase(MQToolBox._TYPEQM))
			objectDescriptorLabel.setText("Lista de colas del QManager: "
					+ objectName);
		if (objectType.equalsIgnoreCase(MQToolBox._TYPEQ))
			objectDescriptorLabel.setText("Lista de mensajes de la Cola: "
					+ objectName);
	}

	public void setTableQueues() {
		isQueue = true;
		backToQList.setEnabled(false);
		if (qmData != null) {
			getQMData();
		}
		switchObjetDescriptorLabelText(MQToolBox._TYPEQM, qmData.getName());
		System.out
				.println("Seteando la tabla de colas para QManaer setTableQueues:"
						+ qmData.getName());
		queuesTable.setModel(qmData.getQueuesTableModel(systemQueueOnOf
				.isSelected(), localQueueTB.isSelected(), remoteQueue
				.isSelected(), aliasQueueTB.isSelected(), clusterQueueTB
				.isSelected()));
		if (!queuesTable.isVisible()) {
			queuesTable.setVisible(true);
		} else {
			queuesTable.repaint();
		}
	}

	private void getQMData() {
		try {
			qmData = toolsImpl.getQMConnected(qmData.getName());
		} catch (FileNotFoundException e) {
			console.setText(e.getMessage());
		} catch (IOException e) {
			console.setText(e.getMessage());
		} catch (MQException e) {
			console.setText("RC: " + e.reasonCode + ". " + e.getMessage());
		}

	}

	public void setTableMessages() {
		isQueue = false;
		backToQList.setEnabled(true);
		switchObjetDescriptorLabelText(MQToolBox._TYPEQ, colaSeleccionada);
		try {
			console.setText("Obteniendo lista de mensajes");
			queuesTable.setModel(toolsImpl.getMessagesTableModel(qmData,
					colaSeleccionada));
			queuesTable.repaint();
			console.setText("Lista de mensajes obtenida");
		} catch (IOException e) {
			console.setText(e.getMessage());
		} catch (MQException e) {
			console.setText("RC: " + e.reasonCode + ". " + e.getMessage());
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == systemQueueOnOf || e.getSource() == localQueueTB
				|| e.getSource() == remoteQueue
				|| e.getSource() == aliasQueueTB
				|| e.getSource() == clusterQueueTB) {
			setTableQueues();
		}

	}

	private void emptyQueue() {

		if (colaSeleccionada != null) {
			int respuesta;
			respuesta = JOptionPane
					.showConfirmDialog(
							new JFrame(),
							"Usted se dispone a vaciar la cola "
									+ colaSeleccionada.trim()
									+ ". Esto puede resultar en incoherencia de datos.\n ¿Realmente desea vaciar la cola?",
							"Advertencia", JOptionPane.OK_CANCEL_OPTION);
			if (respuesta == JOptionPane.OK_OPTION) {

				System.out.println("Cola seleccionada para borrar: "
						+ colaSeleccionada);

				Hashtable properties = new Hashtable();
				properties.put(MQC.CHANNEL_PROPERTY, qmData.getChannel());
				properties.put(MQC.PORT_PROPERTY, qmData.getPort());
				properties.put(MQC.HOST_NAME_PROPERTY, qmData.getHost());

				try {
					toolsImpl.clearMessages(qmData.getName(), colaSeleccionada,
							properties);
				} catch (MQException e) {
					console.setText("RC: " + e.reasonCode + " Mensaje:"
							+ e.getMessage());
				} catch (IOException e) {
					console.setText(e.getMessage());
				}
			}
		} else {
			JOptionPane.showMessageDialog(new Frame("Advertencia"),
					"Debe seleccionar una cola.", "Seleccione una cola.",
					JOptionPane.ERROR_MESSAGE);
		}
		setTableQueues();

	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		TableModel model = queuesTable.getModel();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) qMTree
				.getLastSelectedPathComponent();
		if (node == null
				|| ((String) node.getUserObject())
						.equalsIgnoreCase("Seleccione un QManager para conectarse")) {

			return;
		}
		if (node.isLeaf()
				&& !((String) node.getUserObject())
						.equalsIgnoreCase("Seleccione un QManager para conectarse")) {
			colaSeleccionada = (String) node.getUserObject();
			setTableMessages();
		} else if (!node.isLeaf()) {
			colaSeleccionada = null;
			isQueue = true;
			setTableQueues();
		}

	}

	private void refreshQLists() {
		ComboBoxModel sendCBModel = new DefaultComboBoxModel();
		ComboBoxModel recvCBModel = new DefaultComboBoxModel();
		ComboBoxModel iniCBModel = new DefaultComboBoxModel();
		ComboBoxModel respBModel = new DefaultComboBoxModel();
		if (qmData != null) {
			sendCBModel = qmData.getQueueCBList();
			recvCBModel = qmData.getQueueCBList();
			iniCBModel = qmData.getQueueCBList();
			respBModel = qmData.getQueueCBList();
		}
		sendQueueCB.setModel(sendCBModel);
		recvQueueCB.setModel(recvCBModel);
		iniQCB.setModel(iniCBModel);
		respQCB.setModel(respBModel);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String osName = System.getProperty("os.name");
		console.setText("OSName: "+osName);
		try {
	         if  (osName.startsWith("Windows"))
	            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "http://continuum.cl");
	         else { //assume Unix or Linux
	            String[] browsers = {
	               "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
	            String browser = null;
	            for (int count = 0; count < browsers.length && browser == null; count++)
	               if (Runtime.getRuntime().exec(
	                     new String[] {"which", browsers[count]}).waitFor() == 0)
	                  browser = browsers[count];
	            if (browser == null)
	               throw new Exception("Could not find web browser");
	            else
	               Runtime.getRuntime().exec(new String[] {browser, "http://continuum.cl"});
	            }
	         }
	       catch (Exception e1) {
			console.setText(e1.getMessage());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
