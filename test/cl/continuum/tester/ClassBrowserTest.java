package cl.continuum.tester;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class ClassBrowserTest {
  public static void main(String[] args) {
    JFrame frame = new ClassBrowserTestFrame();
    frame.show();
  }
}

class ClassBrowserTestFrame extends JFrame implements ActionListener,
    TreeSelectionListener {
  public ClassBrowserTestFrame() {
    setTitle("ClassBrowserTest");
    setSize(300, 200);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    // the root of the class tree is Object
    root = new DefaultMutableTreeNode(java.lang.Object.class);
    model = new DefaultTreeModel(root);
    tree = new JTree(model);

    // add this class to populate the tree with some data
    addClass(getClass());

    // set up selection mode
    tree.addTreeSelectionListener(this);
    int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
    tree.getSelectionModel().setSelectionMode(mode);

    // this text area holds the class description
    textArea = new JTextArea();

    // add tree and text area to the content pane
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1, 2));
    panel.add(new JScrollPane(tree));
    panel.add(new JScrollPane(textArea));

    Container contentPane = getContentPane();
    contentPane.add(panel, "Center");

    // new class names are typed into this text
    textField = new JTextField();
    textField.addActionListener(this);
    contentPane.add(textField, "South");
  }

  public void actionPerformed(ActionEvent event) { // add the class whose name
                           // is in the text field
    try {
      String text = textField.getText();
      addClass(Class.forName(text));
      // clear text field to indicate success
      textField.setText("");
    } catch (ClassNotFoundException e) {
      Toolkit.getDefaultToolkit().beep();
    }
  }

  public void valueChanged(TreeSelectionEvent event) { // the user selected a
                             // different
                             // node--update
                             // description
    TreePath path = tree.getSelectionPath();
    if (path == null)
      return;
    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path
        .getLastPathComponent();
    Class c = (Class) selectedNode.getUserObject();
    String description = getFieldDescription(c);
    textArea.setText(description);
  }

  public DefaultMutableTreeNode findUserObject(Object obj) { // find the node
                                 // containing a
                                 // user object
    Enumeration e = root.breadthFirstEnumeration();
    while (e.hasMoreElements()) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
          .nextElement();
      if (node.getUserObject().equals(obj))
        return node;
    }
    return null;
  }

  public DefaultMutableTreeNode addClass(Class c) { // add a new class to the
                            // tree

    // skip non-class types
    if (c.isInterface() || c.isPrimitive())
      return null;

    // if the class is already in the tree, return its node
    DefaultMutableTreeNode node = findUserObject(c);
    if (node != null)
      return node;

    // class isn't present--first add class parent recursively

    Class s = c.getSuperclass();

    DefaultMutableTreeNode parent;
    if (s == null)
      parent = root;
    else
      parent = addClass(s);

    parent = addClass(s);

    // add the class as a child to the parent
    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(c);
    model.insertNodeInto(newNode, parent, parent.getChildCount());

    // make node visible
    TreePath path = new TreePath(model.getPathToRoot(newNode));
    tree.makeVisible(path);

    return newNode;
  }

  public static String getFieldDescription(Class c) { // use reflection to
                            // find types and names
                            // of fields
    String r = "";
    Field[] fields = c.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      Field f = fields[i];
      if ((f.getModifiers() & Modifier.STATIC) != 0)
        r += "static ";
      r += f.getType().getName() + " ";
      r += f.getName() + "\n";
    }
    return r;
  }

  private DefaultMutableTreeNode root;

  private DefaultTreeModel model;

  private JTree tree;

  private JTextField textField;

  private JTextArea textArea;
}
