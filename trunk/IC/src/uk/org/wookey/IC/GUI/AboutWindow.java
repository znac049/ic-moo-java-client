package uk.org.wookey.IC.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutWindow extends JDialog {
	private static final long serialVersionUID = 1L;

	public AboutWindow() {
		super(new JFrame(), "About IC", true);
		
		Box b = Box.createVerticalBox();
		b.add(Box.createGlue());
		b.add(new JLabel("<html><center><h3>IC - the amazing extensibleMOO client.</h3></center>"));
		b.add(new JLabel("<html>Released under <a href=\"#\">GPL V3</a>"));
		b.add(new JLabel("<html>For up to date information, see the project website at<br/><a href=\"https://code.google.com/p/ic-moo-java-client/\">https://code.google.com/p/ic-moo-java-client/</a>"));
		b.add(Box.createGlue());
		getContentPane().add(b, "Center");

		JPanel p2 = new JPanel();
		JButton ok = new JButton("Ok");
		p2.add(ok);
		getContentPane().add(p2, "South");

		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		});

		setSize(400, 250);
	}
}
