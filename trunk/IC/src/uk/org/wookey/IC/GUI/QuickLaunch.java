package uk.org.wookey.IC.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.org.wookey.IC.Utils.Logger;

public class QuickLaunch extends JPanel {
	private static final long serialVersionUID = 1L;
	private Logger _logger = new Logger("QuickLaunch");
	private JTextField world;
	private JTextField port;
	
	public QuickLaunch() {
		super();
	
		JLabel lab;
		
		lab = new JLabel("Quick Connect.");
		add(lab);
		
		lab = new JLabel("World:");
		add(lab);
		
		world = new JTextField();
		world.setColumns(15);
		add(world);
		
		lab = new JLabel("Port:");
		add(lab);
		
		port = new JTextField();
		port.setColumns(4);
		add(port);
		
		JButton btn = new JButton("Connect");
		btn.addActionListener(new MyActionListener());
		add(btn);
	}
	
	private class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int portNum = Integer.parseInt(port.getText());
			
			if (!world.getText().isEmpty() & (portNum > 0)) {
				try {
					WorldTab tab = new WorldTab(world.getText(), portNum);
					ApplicationWindow.addTab(tab);
					tab.runThread();
					
					world.setText("");
					port.setText("");
				} catch (IOException ex) {
					_logger.logMsg("Failed to create WorldTab");
				}
			}
		}
		
	}
}
