package uk.org.wookey.IC.newGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.newUtils.HorizontalPanel;
import uk.org.wookey.IC.newUtils.KeyCode;
import uk.org.wookey.IC.newUtils.KeyMap;
import uk.org.wookey.IC.newUtils.KeyMapping;
import uk.org.wookey.IC.newUtils.Macro;
import uk.org.wookey.IC.newUtils.MacroManager;
import uk.org.wookey.IC.newUtils.VerticalPanel;

public class KeyMapForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private Logger _logger = new Logger("KeyMapForm");
	private WorldTab worldTab;
	
	private JList keyList;
	
	public KeyMapForm(WorldTab tab) {
		super("Key Maps");
		
		worldTab = tab;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(new KeyHandler());

		getContentPane().setLayout(new GridBagLayout());
		layoutContents();
		
		setSize(500, 250);
		setLocation(300, 300);
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	private void layoutContents() {
		Container panel = getContentPane();
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		
		gbc.insets = new Insets(2, 2, 2, 2);
		
		//gbc.fill = GridBagConstraints.BOTH;
		
		gbc.anchor = GridBagConstraints.CENTER;
		
		JLabel lab1 = new JLabel("Current Bindings");
		panel.add(lab1, gbc);
		
		JLabel lab2 = new JLabel("Key to Map");
		gbc.gridx++;
		panel.add(lab2, gbc);

		JLabel lab3 = new JLabel("Macro");
		gbc.gridx++;
		panel.add(lab3, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 3;
		add(makeListOfBindings(worldTab.getKeyMap()), gbc);
		
		gbc.gridx = 1;
		gbc.gridheight = 1;
		add(makeBindingPanel(), gbc);
		
		gbc.gridx = 2;
		gbc.fill = GridBagConstraints.SOUTH;
		add(makeListOfMacros(), gbc);
		
		JTextArea help = new JTextArea();
		help.setLineWrap(true);
		help.setText("willy wily had a coo!");
		help.setEditable(false);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		add(help, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(makeButtonsPanel(), gbc);
		//getContentPane().add(makeListOfBindings(worldTab.getKeyMap()), BorderLayout.LINE_START);
		
		//getContentPane().add(makeBindingPanel(), BorderLayout.CENTER);
		
		//getContentPane().add(makeButtonsPanel(), BorderLayout.PAGE_END);
			
	}
	
	private JScrollPane makeListOfBindings(KeyMap map) {
		// Populate the key list with all existing key mappings
		DefaultListModel model = new DefaultListModel();
		ArrayList<KeyMapping> mappings = map.getMappings();
		for (KeyMapping mapping: mappings) {
			int key = mapping.getKeyCode();
			KeyCode keyCode = new KeyCode(key);
			
			model.addElement(keyCode.toString());
		}

		keyList = new JList(model);
		keyList.setBorder(new LineBorder(Color.black));
		keyList.setSelectedIndex(0);
		
		return new JScrollPane(keyList);
	}
	
	private JComboBox makeListOfMacros() {
		JComboBox macroList = new JComboBox();
		macroList.setAlignmentX(CENTER_ALIGNMENT);
		for (Macro macro: MacroManager.getMacroList()) {
			macroList.addItem(new ComboItem(macro.getName(), macro.getName()));
		}
		macroList.setEditable(true);

		return macroList;
	}
	
	private JPanel makeBindingPanel() {
		JPanel codeBox = new JPanel();
		codeBox.setAlignmentX(CENTER_ALIGNMENT);
		codeBox.setBorder(new LineBorder(Color.black));
		codeBox.setPreferredSize(new Dimension(100, 100));
		codeBox.setBackground(new Color(0, 128, 128));
		
		return codeBox;
	}

	private JPanel makeButtonsPanel() {
		HorizontalPanel panel = new HorizontalPanel();
		
		ButtonHandler mouseHandler = new ButtonHandler();

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(mouseHandler);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(mouseHandler);
		
		panel.add(saveButton);
		panel.add(cancelButton);

		return panel;
	}
	
	class ComboItem {
	    private String _key;
	    private String _value;

	    public ComboItem(String key, String value) {
	        _key = key;
	        _value = value;
	    }

	    @Override
	    public String toString() {
	        return _key;
	    }

	    public String getKey() {
	        return _key;
	    }

	    public String getValue() {
	        return _value;
	    }
	}
	
	class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			_logger.logInfo("Button click: '" + cmd + "'");
			
			if (cmd.equalsIgnoreCase("Cancel")) {
				setVisible(false);
				dispose();
			}
			else if (cmd.equalsIgnoreCase("Save")) {
				_logger.logInfo("Need to code save");
				worldTab.getKeyMap().save(worldTab.getPrefs());
				setVisible(false);
				dispose();
			}
			
		}		
	}
	
	class KeyHandler implements KeyListener {
		private KeyCode keyCode = new KeyCode(0);

		@Override
		public void keyPressed(KeyEvent keyEvent) {
			int key = keyEvent.getKeyCode();
			
			switch (key) {
			case KeyEvent.VK_ALT:
				keyCode.alt(true);;
				break;

			case KeyEvent.VK_CONTROL:
				keyCode.ctrl(true);;
				break;
			
			case KeyEvent.VK_SHIFT:
				keyCode.shift(true);
				break;
			
			case KeyEvent.VK_WINDOWS:
				keyCode.windows(true);
				break;
				
			default:
				// some other key. take an interest if any of the magic keys are also pressed
				keyCode.set(key);
				if (keyCode.nonPrintable()) {
					_logger.logInfo("Got key: " + keyCode.toString());
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent keyEvent) {
			int key = keyEvent.getKeyCode();
			
			switch (key) {
			case KeyEvent.VK_ALT:
				keyCode.alt(false);;
				break;

			case KeyEvent.VK_CONTROL:
				keyCode.ctrl(false);;
				break;
			
			case KeyEvent.VK_SHIFT:
				keyCode.shift(false);
				break;
			
			case KeyEvent.VK_WINDOWS:
				keyCode.windows(false);
				break;

			default:
				keyCode.set(0);
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}	
	}
	
	
}
