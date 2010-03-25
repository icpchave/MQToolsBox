package cl.continuum.mqtoolkit.main;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import cl.continuum.mqtoolkit.model.QManagerData;
import cl.continuum.mqtoolkit.util.FileUtil;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class QMConfigData extends javax.swing.JDialog implements ActionListener{
	private JPanel jPanel1;
	private JTextField channelText;
	private JLabel blankLabel;
	private JButton aceptarJB;
	private JButton resetJB;
	private JButton cancelarJB;
	private JTextField nameText;
	private JLabel jLabel1;
	private JLabel channelLabel;
	private JTextField portText;
	private JLabel portLabel;
	private JTextField hostText;
	private JLabel hostLabel;
	private JLabel nameLabel;
	private QManagerData qmData = new QManagerData();
	MQToolBox tb;


	public QMConfigData(JFrame frame, MQToolBox tb) {
		super(frame,"ADd QManager",true);
		this.tb = tb;
		//initGUI();
	}
	public QMConfigData(MQToolBox tb) {
		super();
		this.tb = tb;
		//initGUI();
	}
	
	public void initGUI() {
		try {
			{
				this.setResizable(false);
				this
				.setIconImage(new ImageIcon(
						getClass()
								.getClassLoader()
								.getResource(
										"cl/continuum/mqtoolkit/resources/icons/add-server-icon-32.png"))
						.getImage());
			}
			{
			}
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1, BorderLayout.CENTER);
				jPanel1.setPreferredSize(new java.awt.Dimension(252, 365));
				{
					nameLabel = new JLabel();
					jPanel1.add(nameLabel);
					nameLabel.setText("Adicionar QManager");
					nameLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("cl/continuum/mqtoolkit/resources/icons/add-server-icon-48.png")));
					nameLabel.setFont(new java.awt.Font("Tahoma",1,12));
					nameLabel.setPreferredSize(new java.awt.Dimension(182, 74));
				}
				{
					hostLabel = new JLabel();
					jPanel1.add(hostLabel);
					hostLabel.setText("Host/IP");
					hostLabel.setPreferredSize(new java.awt.Dimension(188, 14));
				}
				{
					hostText = new JTextField();
					jPanel1.add(hostText);
					hostText.setText("localhost");
					hostText.setPreferredSize(new java.awt.Dimension(166, 21));
				}
				{
					portLabel = new JLabel();
					jPanel1.add(portLabel);
					portLabel.setText("Puerto");
					portLabel.setPreferredSize(new java.awt.Dimension(188, 14));
				}
				{
					portText = new JTextField();
					jPanel1.add(portText);
					portText.setText("1414");
					portText.setPreferredSize(new java.awt.Dimension(166, 21));
				}
				{
					channelLabel = new JLabel();
					jPanel1.add(channelLabel);
					channelLabel.setText("Canal");
					channelLabel.setPreferredSize(new java.awt.Dimension(188, 14));
				}
				{
					channelText = new JTextField();
					jPanel1.add(channelText);
					channelText.setText("SYSTEM.DEF.SVRCONN");
					channelText.setPreferredSize(new java.awt.Dimension(166, 21));
				}
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setText("Nombre");
					jLabel1.setPreferredSize(new java.awt.Dimension(188, 14));
				}
				{
					nameText = new JTextField();
					jPanel1.add(nameText);
					nameText.setPreferredSize(new java.awt.Dimension(166, 21));
				}
				{
					blankLabel = new JLabel();
					jPanel1.add(blankLabel);
					blankLabel.setPreferredSize(new java.awt.Dimension(231, 30));
				}
				{
					cancelarJB = new JButton();
					jPanel1.add(cancelarJB);
					cancelarJB.setText("Cancelar");
					cancelarJB.setPreferredSize(new java.awt.Dimension(82, 21));
					cancelarJB.addActionListener(this);
				}
				{
					resetJB = new JButton();
					jPanel1.add(resetJB);
					resetJB.setText("Resetear");
					resetJB.setPreferredSize(new java.awt.Dimension(71, 21));
					resetJB.addActionListener(this);
				}
				{
					aceptarJB = new JButton();
					jPanel1.add(aceptarJB);
					aceptarJB.setText("Aceptar");
					aceptarJB.setPreferredSize(new java.awt.Dimension(75, 21));
					aceptarJB.addActionListener(this);
				}
			}
			/*SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					JFrame frame = new JFrame();
					QMConfigData inst = new QMConfigData(frame,tb);
					inst.setVisible(true);
				}
			});*/
			//setVisible(true);
			//pack();
			this.setSize(260, 365);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		FileUtil fu = new FileUtil();
		if (evt.getSource() == aceptarJB) {
			tb.console.setText("Aceptando");
			if(channelText.getText().isEmpty()||hostText.getText().isEmpty()||nameText.getText().isEmpty()||portText.getText().isEmpty()){
				JOptionPane.showMessageDialog(new Frame("Advertencia"),
						"Todos los campos deben estar llenos.",
						"Debe llenar los campos.", JOptionPane.ERROR_MESSAGE);
			}else{
			qmData.setChannel(channelText.getText());
			qmData.setHost(hostText.getText());
			qmData.setName(nameText.getText());
			qmData.setPort(Integer.parseInt(portText.getText()));
			try {
				fu.saveQManager(qmData);
				tb.refreshQMList();
				
			} catch (FileNotFoundException e) {
				tb.console.setText( e.getMessage());
			} catch (IOException e) {
				tb.console.setText( e.getMessage());
			}
			setVisible(false);
			dispose();
			}
		} else if (evt.getSource() == cancelarJB) {
			setVisible(false);
			dispose();
		}else if (evt.getSource() == resetJB) {
			hostText.setText("localhost");
			portText.setText("1414");
			channelText.setText("SYSTEM.DEV.SVRCONN");
			nameText.setText("");
			
		}
		
	}

}
