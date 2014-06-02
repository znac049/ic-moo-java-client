package uk.org.wookey.IC.GUI;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QuickLaunch extends JPanel {
	private JTextField world;
	private JTextField port;
	
	public QuickLaunch() {
		super();
		
		world = new JTextField();
		port = new JTextField();
		
		add(new JLabel("World:"));
		add(world);
		add(new JLabel("Port:"));
		add(port);
	}
}
