package uk.org.wookey.IC.GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Application.MainApplication;
import uk.org.wookey.IC.GUI.Forms.HighlightForm;
import uk.org.wookey.IC.GUI.Forms.KeyMapForm;
import uk.org.wookey.IC.GUI.Forms.MacroManagerForm;
import uk.org.wookey.IC.GUI.Forms.SettingsForm;
import uk.org.wookey.IC.GUI.Forms.WorldForm;
import uk.org.wookey.IC.Utils.KeyMap;
import uk.org.wookey.IC.Utils.Logger;


public class MainMenuBar extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String FILE_TEXT = "File";
	private static final String EXIT_TEXT = "Exit";
	private static final String TOOLS_TEXT = "Tools";
	private static final String WORLDS_TEXT = "Worlds";
	private static final String HIGHLIGHTS_TEXT = "Highlights";
	private static final String SETTINGS_TEXT = "Settings";
	private static final String MACROS_TEXT = "Macros";
	private static final String KEYMAP_TEXT = "Key Map";
	private static final String LOGSESSION_TEXT = "Log Session";
	private static final String HELP_TEXT = "Help";
	private static final String ABOUT_TEXT = "About";
	
	private Logger _logger = new Logger("MainMenu");
	private JTabbedPane _tabs = null;

	public MainMenuBar() {
		JMenu fileMenu = new JMenu(FILE_TEXT);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem exitItem = new JMenuItem(EXIT_TEXT);
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
		exitItem.addActionListener(this);
		fileMenu.add(exitItem);
		add(fileMenu);
				
		JMenu toolMenu = new JMenu(TOOLS_TEXT);
		
		JMenuItem worldItem = new JMenuItem(WORLDS_TEXT);
		worldItem.setMnemonic(KeyEvent.VK_W);
		worldItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
		worldItem.addActionListener(this);
		toolMenu.add(worldItem);
		
		JMenuItem highlightItem = new JMenuItem(HIGHLIGHTS_TEXT);
		highlightItem.setMnemonic(KeyEvent.VK_H);
		highlightItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
		highlightItem.addActionListener(this);
		toolMenu.add(highlightItem);
		
		JMenuItem settingsItem = new JMenuItem(SETTINGS_TEXT);
		settingsItem.setMnemonic(KeyEvent.VK_S);
		settingsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		settingsItem.addActionListener(this);
		toolMenu.add(settingsItem);
		
		JMenuItem macrosItem = new JMenuItem(MACROS_TEXT);
		macrosItem.addActionListener(this);
		toolMenu.add(macrosItem);
		
		JMenuItem keysItem = new JMenuItem(KEYMAP_TEXT);
		keysItem.addActionListener(this);
		toolMenu.add(keysItem);
		
		JCheckBoxMenuItem logSession = new JCheckBoxMenuItem(LOGSESSION_TEXT);
		logSession.setSelected(false);
		logSession.addActionListener(this);
		toolMenu.add(logSession);

		add(toolMenu);
		
		JMenu helpMenu = new JMenu(HELP_TEXT);
		helpMenu.setMnemonic(KeyEvent.VK_H);

		JMenuItem aboutItem = new JMenuItem(ABOUT_TEXT);
		aboutItem.addActionListener(this);
		helpMenu.add(aboutItem);
		
		add(helpMenu);
	}
	
	public void addTabs(JTabbedPane tabs) {
		_tabs = tabs;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		_logger.logInfo("Main menu click: '" + cmd + "'");
		
		if (cmd.equalsIgnoreCase(WORLDS_TEXT)) {
			new WorldForm();
		}			
		else if (cmd.equalsIgnoreCase(HIGHLIGHTS_TEXT)) {
			new HighlightForm();
		}
		else if (cmd.equalsIgnoreCase(SETTINGS_TEXT)) {
			new SettingsForm();
		}
		else if (cmd.equalsIgnoreCase(MACROS_TEXT)) {
			new MacroManagerForm();
		}
		else if (cmd.equalsIgnoreCase(LOGSESSION_TEXT)) {
			if (_tabs != null) {
				Component tab = _tabs.getSelectedComponent();
				
				if (tab instanceof WorldTab) {
					_logger.logInfo("Change world session logging");
					
					WorldTab worldTab = (WorldTab) tab;
					worldTab.getServerPort().setLogging(((JCheckBoxMenuItem)e.getSource()).isSelected());
				}
			}
		}
		else if (cmd.equalsIgnoreCase(EXIT_TEXT)) {
			System.exit(0);
		}
		else if (cmd.equalsIgnoreCase(KEYMAP_TEXT)) {
			if (_tabs != null) {
				Component tab = _tabs.getSelectedComponent();
				
				if (tab instanceof WorldTab) {
					_logger.logInfo("Find the keymap of the worldtab");
					new KeyMapForm((WorldTab) tab);
				}
			}
		}
		else if (cmd.equalsIgnoreCase(ABOUT_TEXT)) {
			JDialog about = new AboutWindow();
			about.setVisible(true);
		}
	}
}
