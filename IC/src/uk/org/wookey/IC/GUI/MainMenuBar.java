package uk.org.wookey.IC.GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.newGUI.KeyMapForm;
import uk.org.wookey.IC.newGUI.WorldTab;
import uk.org.wookey.IC.newUtils.KeyMap;


public class MainMenuBar extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Logger _logger = new Logger("MainMenu");
	private JTabbedPane _tabs = null;

	public MainMenuBar() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
		exitItem.addActionListener(this);
		fileMenu.add(exitItem);
		add(fileMenu);
				
		JMenu toolMenu = new JMenu("Tools");
		
		JMenuItem worldItem = new JMenuItem("Worlds");
		worldItem.setMnemonic(KeyEvent.VK_W);
		worldItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
		worldItem.addActionListener(this);
		toolMenu.add(worldItem);
		
		JMenuItem highlightItem = new JMenuItem("Highlights");
		highlightItem.setMnemonic(KeyEvent.VK_H);
		highlightItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
		highlightItem.addActionListener(this);
		toolMenu.add(highlightItem);
		
		JMenuItem settingsItem = new JMenuItem("Settings");
		settingsItem.setMnemonic(KeyEvent.VK_S);
		settingsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		settingsItem.addActionListener(this);
		toolMenu.add(settingsItem);
		
		JMenuItem macrosItem = new JMenuItem("Macros");
		macrosItem.addActionListener(this);
		toolMenu.add(macrosItem);
		
		JMenuItem keysItem = new JMenuItem("Key Map");
		keysItem.addActionListener(this);
		toolMenu.add(keysItem);

		add(toolMenu);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.add(new JMenuItem("About"));
		
		add(helpMenu);
	}
	
	public void addTabs(JTabbedPane tabs) {
		_tabs = tabs;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		_logger.logInfo("Main menu click: '" + cmd + "'");
		
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
		else if (cmd.equalsIgnoreCase("Key Map")) {
			if (_tabs != null) {
				Component tab = _tabs.getSelectedComponent();
				
				if (tab instanceof WorldTab) {
					_logger.logInfo("Find the keymap of the worldtab");
					new KeyMapForm(((WorldTab) tab).getKeyMap());
				}
			}
		}
	}
}
