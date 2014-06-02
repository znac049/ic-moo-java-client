package uk.org.wookey.IC.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QuickLaunch extends JPanel {
	private JTextField world;
	private JTextField port;
	
	public QuickLaunch() {
		super();
		
		world = new JTextField();
		world.setColumns(20);
		
		port = new JTextField();
		port.setColumns(6);
		
		add(new JLabel("World:"));
		add(world);
		add(new JLabel("Port:"));
		add(port);
	}
}
