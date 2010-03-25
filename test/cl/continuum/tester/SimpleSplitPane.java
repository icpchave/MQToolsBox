package cl.continuum.tester;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class SimpleSplitPane extends JFrame {

  static String sometext = "This is a simple text string that is long enough "
      + "to wrap over a few lines in the simple demo we're about to build.  "
      + "We'll put two text areas side by side in a split pane.";

  public SimpleSplitPane() {
    super("Simple SplitPane Frame");
    setSize(450, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JTextArea jt1 = new JTextArea(sometext);
    JTextArea jt2 = new JTextArea(sometext);

    // Make sure our text boxes do line wrapping and have reasonable
    // minimum sizes.
    jt1.setLineWrap(true);
    jt2.setLineWrap(true);
    jt1.setMinimumSize(new Dimension(150, 150));
    jt2.setMinimumSize(new Dimension(150, 150));
    jt1.setPreferredSize(new Dimension(250, 200));
    JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jt1, jt2);
    getContentPane().add(sp, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    SimpleSplitPane ssb = new SimpleSplitPane();
    ssb.setVisible(true);
  }
}

       