package cl.continuum.tester;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;


/**
 * Clase de ejemplo sencillo de uso del JTree
 *
 * @author Chuidiang
 */
public class PruebaJTree2
{
    /**
     * Ejemplo sencillo de uso de JTree
     *
     * @param args Argumentos de linea de comandos. Se ignoran.
     */
    public static void main(String[] args)
    {
       PruebaJTree2 p = new PruebaJTree2();
       p.showTree();
    }
    public void showTree(){
    	 // Construccion del arbol
        
        JTree tree = new JTree(getTreeComponents());

        // Cambiamos los iconos
        DefaultTreeCellRenderer render= (DefaultTreeCellRenderer)tree.getCellRenderer();
        render.setLeafIcon(new ImageIcon(getClass().getClassLoader().getResource("cl/continuum/mqtoolkit/resources/icons/cola_local.png")));
        render.setOpenIcon(new ImageIcon(getClass().getClassLoader().getResource("cl/continuum/mqtoolkit/resources/icons/server_conectado(2).png")));
        render.setClosedIcon(new ImageIcon(getClass().getClassLoader().getResource("cl/continuum/mqtoolkit/resources/icons/server_conectado(2).png")));
        render.setIcon(new ImageIcon(getClass().getClassLoader().getResource("cl/continuum/mqtoolkit/resources/icons/Toolbox-32x32.png")));
		
        // Construccion y visualizacion de la ventana
        JFrame v = new JFrame();
        JScrollPane scroll = new JScrollPane(tree);
        v.getContentPane().add(scroll);
        v.pack();
        v.setVisible(true);
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    private DefaultMutableTreeNode[] getTreeComponents(){
    	DefaultMutableTreeNode abuelo = new DefaultMutableTreeNode("abuelo");
    	DefaultMutableTreeNode abuela = new DefaultMutableTreeNode("abuela");
    	DefaultMutableTreeNode[] abuelos = {abuelo,abuela};
    	 // Construccion de los datos del arbol
        DefaultMutableTreeNode padre = new DefaultMutableTreeNode("padre");
        DefaultMutableTreeNode tio = new DefaultMutableTreeNode("tio");
        abuelo.add(padre);
        abuelo.add(tio);
        DefaultMutableTreeNode hijo = new DefaultMutableTreeNode("hijo");
        DefaultMutableTreeNode hija = new DefaultMutableTreeNode("hija");
        padre.add(hijo);
        padre.add(hija);
        return abuelos;
    	
    }
}