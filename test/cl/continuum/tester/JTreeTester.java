package cl.continuum.tester;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class JTreeTester extends JFrame {

  public static void main( String[] argv ) {
	  JTreeTester myExample = new JTreeTester( "Simple JTree Example" );
  }

  public JTreeTester ( String title ) {
    super( title );
    setSize( 150, 150 );
    addWindowListener( new WindowAdapter() {
      public void windowClosing( WindowEvent we ) {
        dispose();
        System.exit( 0 );
      }
    } );
    init();
    pack();
    setVisible( true );
  }

  private void init() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode( "Calendar" );
    DefaultMutableTreeNode months = new DefaultMutableTreeNode( "Months" );
    root.add( months );
    String monthLabels[] = {"January", "February", "March", "April", "May",
                            "June", "July", "August", "September", "October", "November", "December"};
    for( int i = 0; i < monthLabels.length; i++ )
      months.add( new DefaultMutableTreeNode( monthLabels[i] ) );
    DefaultMutableTreeNode weeks = new DefaultMutableTreeNode( "Weeks" );
    root.add( weeks );
    String weekLabels[] = {"Monday", "Tuesday", "Wednesday", "Thursday",
                           "Friday", "Saturday", "Sunday"};
    for( int i = 0; i < weekLabels.length; i++ )
      weeks.add( new DefaultMutableTreeNode( weekLabels[i] ) );
    JScrollPane js = new JScrollPane( new JTree( root ) );
    getContentPane().add( js );
  }
}

