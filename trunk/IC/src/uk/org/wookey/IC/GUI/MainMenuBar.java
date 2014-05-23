package uk.org.wookey.IC.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MainMenuBar extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = 3792156611099670102L;

	public MainMenuBar() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem worldItem = new JMenuItem("Worlds");
		worldItem.setMnemonic(KeyEvent.VK_W);
		worldItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
		worldItem.addActionListener(this);
		fileMenu.add(worldItem);
		
		JMenuItem highlightItem = new JMenuItem("Highlights");
		highlightItem.setMnemonic(KeyEvent.VK_H);
		highlightItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
		highlightItem.addActionListener(this);
		fileMenu.add(highlightItem);
		
		JMenuItem settingsItem = new JMenuItem("Settings");
		settingsItem.setMnemonic(KeyEvent.VK_S);
		settingsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		settingsItem.addActionListener(this);
		fileMenu.add(settingsItem);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
		exitItem.addActionListener(this);
		fileMenu.add(exitItem);
		add(fileMenu);
				
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.add(new JMenuItem("About"));
		
		add(helpMenu);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equalsIgnoreCase("Worlds")) {
			new WorldForm();
		}			
		else if (cmd.equalsIgnoreCase("Highlights")) {
			new HighlightForm();
		}
		else if (cmd.equalsIgnoreCase("Settings")) {
			new SettingsForm();
		}
		else if (cmd.equalsIgnoreCase("Exit")) {
			System.exit(0);
		}
	}
}
