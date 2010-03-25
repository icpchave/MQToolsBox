/**
 * 
 */
package cl.continuum.tester;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;


/**
 * @author israel
 *
 */
public class Inicio extends JFrame{
	private JButton addQM = new JButton(new ImageIcon(getClass().getClassLoader().getResource("cl/continuum/mqtoolkit/resources/icons/add-server-icon-48.png")));
	private JButton remQM= new JButton(new ImageIcon(getClass().getClassLoader().getResource("cl/continuum/mqtoolkit/resources/icons/desable-server-icon-48.png")));
	private JTree QMTree;
	{
		addQM.setSize(80, 80);
	}{
		remQM = new JButton(new ImageIcon(getClass().getClassLoader().getResource("cl/continuum/mqtoolkit/resources/icons/remove_server.ico")));
		remQM.setSize(80, 80);
	}
	//Aqui van los splitpanes con los mensajes de entrada para el test funcional
	private JSplitPane funcionalSendSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	//Aqui van los splitpanes con los mensajes de salida para el test funcional
	private JSplitPane funcionalReceiverSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	//Aqui van los splitpanes con los mensajes de entrada y salida para el test funcional
	private JSplitPane functionalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,funcionalSendSplitPane,funcionalReceiverSplitPane);
	//Aqui van los botones de comandos +QM -QM y QMTree
	private JSplitPane comandosSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT );
	//TODO Principal
	private JSplitPane mainSplitPane; 
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Inicio inst = new Inicio();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	public Inicio() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		this.setSize(1000, 700);
		
	}
	
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
			this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("cl/continuum/mqtoolkit/resources/icons/Toolbox-32x32.png")).getImage());
			this.setTitle("Websphere MQ Toolkit");
			mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,comandosSplitPane,functionalSplitPane);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
