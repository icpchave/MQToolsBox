package cl.continuum.tester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class JSample extends JFrame implements ActionListener {
	JButton b1;
	JButton b2;
	JRadioButton r1;
	JRadioButton r2;
	JRadioButton r3;
	JRadioButton r4;
	JCheckBox c1;
	JTextField txt1;
	JTextField txt2;

	public JSample() {
		Container p = getContentPane();
		JPanel jp = new JPanel();
		ButtonGroup bg = new ButtonGroup();
		p.add(jp);

		bg.add(r1 = new JRadioButton("Mutiplication"));
		bg.add(r2 = new JRadioButton("Addtion"));
		bg.add(r3 = new JRadioButton("Subtraction"));
		bg.add(r4 = new JRadioButton("Division"));
		jp.add(r1);
		jp.add(r2);
		jp.add(r3);
		jp.add(r4);
		jp.add(txt1 = new JTextField(5));
		jp.add(txt2 = new JTextField(5));
		jp.add(b1 = new JButton("Show Answer"));
		jp.add(b2 = new JButton("Clear"));
		b1.addActionListener(this);
		b2.addActionListener(this);
	}

	public static void main(String[] args) {
		JSample js = new JSample();
		js.setVisible(true);
		js.setSize(400, 100);
		js.setTitle("Basic Aritmetic Operations - *, +, -, /");
		js.setResizable(false);
	}

	public void actionPerformed(ActionEvent e) {
		double ans = 0.0;
		JOptionPane j = new JOptionPane();
		if (e.getSource() == b1) {
			if (r1.isSelected()) {
				ans = Double.parseDouble(txt2.getText())
						* Double.parseDouble(txt1.getText());
				j.showMessageDialog(null, "Answer: " + ans);
			} else if (r2.isSelected()) {
				ans = Double.parseDouble(txt2.getText())
						+ Double.parseDouble(txt1.getText());
				j.showMessageDialog(null, "Answer: " + ans);
			} else if (r3.isSelected()) {
				ans = Double.parseDouble(txt2.getText())
						- Double.parseDouble(txt1.getText());
				j.showMessageDialog(null, "Answer: " + ans);
			} else if (r4.isSelected()) {
				ans = Double.parseDouble(txt2.getText())
						/ Double.parseDouble(txt1.getText());
				j.showMessageDialog(null, "Answer: " + ans);
			} else {
				j.showMessageDialog(null, "Select first Operation", "Error",
						JOptionPane.ERROR_MESSAGE);
				txt1.setText("");
				txt2.setText("");
			}
		} else if (e.getSource() == b2) {
			txt1.setText("");
			txt2.setText("");
		}
	}
}