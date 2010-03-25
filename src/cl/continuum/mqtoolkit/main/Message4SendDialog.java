package cl.continuum.mqtoolkit.main;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import cl.continuum.mqtoolkit.impl.MQPFCImpl;
import cl.continuum.mqtoolkit.impl.ToolsImpl;
import cl.continuum.mqtoolkit.model.MessageData;
import cl.continuum.mqtoolkit.model.QManagerData;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;


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
public class Message4SendDialog extends JDialog implements ActionListener{

	private QManagerData qmData;
	private String qName;
	private MQToolBox tBox;
	
	private JComboBox persistenciaCB;
	private JLabel formatLabel;
	private JTextField codificacionText;
	private JButton cancelB;
	private JButton sendMessageB;
	private JButton cancelMessPropertiesB;
	private JTextArea loadMsgBodyText;
	private JScrollPane messageBodySP;
	private JLabel codificacionLabel;
	private JTextField charsetIdText;
	private JLabel charsetIDLabel;
	private JTextField formatText;
	private JTextField replyToQMText;
	private JLabel replyToQMLabel;
	private JTextField replyToQText;
	private JLabel replyToQLabel;
	private JTextField caducidadText;
	private JLabel caducidadLabel;
	private JLabel percistanceLabel;
	private JTextField grupoIDText;
	private JLabel groupIdLabel;
	private JTextField correlIDText;
	private JLabel correlID;
	private JLabel messPropertiesLabel;
	private JPanel messagePropertiesPanel;

	public Message4SendDialog(QManagerData qmData, String qName, MQToolBox tBox) {
		super();
		this.qmData = qmData;
		this.qName = qName;
		this.tBox = tBox;
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("cl/continuum/mqtoolkit/resources/icons/Toolbox-32x32.png")).getImage());
			{
				messagePropertiesPanel = new JPanel();
				getContentPane().add(messagePropertiesPanel, BorderLayout.CENTER);
				messagePropertiesPanel.setPreferredSize(new java.awt.Dimension(380,539));
				messagePropertiesPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,new java.awt.Color(192,192,192),null,null,new java.awt.Color(192,192,192)));
				{
					messPropertiesLabel = new JLabel();
					messagePropertiesPanel.add(messPropertiesLabel);
					messPropertiesLabel.setText("Propiedades del Mensaje");
					messPropertiesLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("cl/continuum/mqtoolkit/resources/icons/E-Mail-Config-64.png")));
					messPropertiesLabel.setFont(new Font("Dialog",Font.BOLD,18));
					messPropertiesLabel.setPreferredSize(new java.awt.Dimension(311,64));
				}
				{
					correlID = new JLabel();
					messagePropertiesPanel.add(correlID);
					correlID.setText("Correlational ID");
					correlID.setPreferredSize(new java.awt.Dimension(231,14));
				}
				{
					correlIDText = new JTextField();
					messagePropertiesPanel.add(correlIDText);
					correlIDText.setPreferredSize(new java.awt.Dimension(126,21));
				}
				{
					groupIdLabel = new JLabel();
					messagePropertiesPanel.add(groupIdLabel);
					groupIdLabel.setText("Identidad de grupo");
					groupIdLabel.setPreferredSize(new java.awt.Dimension(231,14));
				}
				{
					grupoIDText = new JTextField();
					messagePropertiesPanel.add(grupoIDText);
					grupoIDText.setPreferredSize(new java.awt.Dimension(126,21));
				}
				{
					percistanceLabel = new JLabel();
					messagePropertiesPanel.add(percistanceLabel);
					percistanceLabel.setText("Percistencia");
					percistanceLabel.setPreferredSize(new java.awt.Dimension(231,14));
				}
				{
					ComboBoxModel persistenciaCBModel = 
						new DefaultComboBoxModel(
								new String[] { "Si", "No", "Según Cola" });
					persistenciaCB = new JComboBox();
					messagePropertiesPanel.add(persistenciaCB);
					persistenciaCB.setModel(persistenciaCBModel);
					persistenciaCB.setPreferredSize(new java.awt.Dimension(126,21));
				}
				{
					caducidadLabel = new JLabel();
					messagePropertiesPanel.add(caducidadLabel);
					caducidadLabel.setText("Caducidad");
					caducidadLabel.setPreferredSize(new java.awt.Dimension(231,14));
				}
				{
					caducidadText = new JTextField();
					messagePropertiesPanel.add(caducidadText);
					caducidadText.setText("00000");
					caducidadText.setPreferredSize(new java.awt.Dimension(126,21));
				}
				{
					replyToQLabel = new JLabel();
					messagePropertiesPanel.add(replyToQLabel);
					replyToQLabel.setText("Cola de respuesta");
					replyToQLabel.setPreferredSize(new java.awt.Dimension(231,14));
				}
				{
					replyToQText = new JTextField();
					messagePropertiesPanel.add(replyToQText);
					replyToQText.setPreferredSize(new java.awt.Dimension(126,21));
				}
				{
					replyToQMLabel = new JLabel();
					messagePropertiesPanel.add(replyToQMLabel);
					replyToQMLabel.setText("QManager de respuesta");
					replyToQMLabel.setPreferredSize(new java.awt.Dimension(231,14));
				}
				{
					replyToQMText = new JTextField();
					messagePropertiesPanel.add(replyToQMText);
					replyToQMText.setPreferredSize(new java.awt.Dimension(126,21));
				}
				{
					formatLabel = new JLabel();
					messagePropertiesPanel.add(formatLabel);
					formatLabel.setText("Formato");
					formatLabel.setPreferredSize(new java.awt.Dimension(231,14));
				}
				{
					formatText = new JTextField();
					messagePropertiesPanel.add(formatText);
					formatText.setText("MQSTR");
					formatText.setPreferredSize(new java.awt.Dimension(126,21));
				}
				{
					charsetIDLabel = new JLabel();
					messagePropertiesPanel.add(charsetIDLabel);
					charsetIDLabel.setText("Identificador de juego de caracteres codificado");
					charsetIDLabel.setPreferredSize(new java.awt.Dimension(231,14));
				}
				{
					charsetIdText = new JTextField();
					messagePropertiesPanel.add(charsetIdText);
					charsetIdText.setPreferredSize(new java.awt.Dimension(126,21));
				}
				{
					codificacionLabel = new JLabel();
					messagePropertiesPanel.add(codificacionLabel);
					codificacionLabel.setText("Codificación");
					codificacionLabel.setPreferredSize(new java.awt.Dimension(231,14));
				}
				{
					codificacionText = new JTextField();
					messagePropertiesPanel.add(codificacionText);
					codificacionText.setPreferredSize(new java.awt.Dimension(126,21));
				}
				{
					messageBodySP = new JScrollPane();
					messagePropertiesPanel.add(messageBodySP);
					messageBodySP.setPreferredSize(new java.awt.Dimension(371,193));
					{
						loadMsgBodyText = new JTextArea();
						messageBodySP.setViewportView(loadMsgBodyText);
						loadMsgBodyText.setText("Escriba aqui el cuerpo del mensaje, incluya # para correlativos numéricos");
					}
				}
				{
					cancelMessPropertiesB = new JButton();
					messagePropertiesPanel.add(cancelMessPropertiesB);
					cancelMessPropertiesB.setText("Restablecer");
					cancelMessPropertiesB.setPreferredSize(new java.awt.Dimension(83, 23));
					cancelMessPropertiesB.addActionListener(this);
				}
				{
					sendMessageB = new JButton();
					messagePropertiesPanel.add(sendMessageB);
					sendMessageB.setText("Enviar");
					sendMessageB.setPreferredSize(new java.awt.Dimension(83, 23));
					sendMessageB.addActionListener(this);
				}
				{
					cancelB = new JButton();
					messagePropertiesPanel.add(cancelB);
					cancelB.setText("Cancelar");
					cancelB.setPreferredSize(new java.awt.Dimension(83, 23));
					cancelB.addActionListener(this);
				}
			}
			pack();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancelB){
			clearAndHide();
		}else if (e.getSource() == sendMessageB) {
			try {
				enviaMensaje();
				if(this.tBox.isQueue)this.tBox.setTableQueues();else {
					this.tBox.setTableMessages();
				}
			} catch (MQException e1) {
				tBox.console.setText("RC: "+e1.reasonCode+". "+e1.getMessage());
			} catch (IOException e1) {
				tBox.console.setText(e1.getMessage());
			}
		}else if (e.getSource() == cancelMessPropertiesB) {
			clear();
		}
	}
	
	private void enviaMensaje() throws MQException, IOException {
		ToolsImpl impl = new ToolsImpl();
		MessageData mensaje = new MessageData();
		if(!caducidadText.getText().isEmpty())mensaje.setCaducidad(Long.parseLong(caducidadText.getText()));
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
		
		impl.putMessages(qmData, qName, mensaje);
		// TODO Auto-generated method stub
		
	}

	private void clearAndHide() {
        clear();
		setVisible(false);
		dispose();
    }
	private void clear(){
		codificacionText.setText("546");
		loadMsgBodyText.setText("");
		charsetIdText.setText("1208");
		formatText.setText("MQSTR");
		replyToQMText.setText("");
		replyToQText.setText("");
		caducidadText.setText("Ilimitado");
		grupoIDText.setText("");
		correlIDText.setText("");
	}
	
	


}
