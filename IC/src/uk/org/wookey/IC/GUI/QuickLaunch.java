package uk.org.wookey.IC.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class QuickLaunch extends JPanel {
	private JTextField world;
	private JTextField port;
	
	public QuickLaunch() {
		super();
	
		JLabel lab;
		
		lab = new JLabel("Quick Connect");
		add(lab);
		
		lab = new JLabel("World");
		add(lab);
		
		world = new JTextField();
		world.setColumns(10);
		add(world);
		
		lab = new JLabel("Port");
		add(lab);
		
		port = new JTextField();
		port.setColumns(6);
		add(port);
		
		add(new JButton("Connect"));
	}
}
