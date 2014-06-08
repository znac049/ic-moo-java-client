package uk.org.wookey.IC.newGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.newUtils.KeyCode;
import uk.org.wookey.IC.newUtils.KeyMap;
import uk.org.wookey.IC.newUtils.KeyMapping;
import uk.org.wookey.IC.newUtils.Macro;
import uk.org.wookey.IC.newUtils.MacroManager;
import webBoltOns.layoutManager.GridFlowLayout;
import webBoltOns.layoutManager.GridFlowLayoutParameter;

public class KeyMapForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private Logger _logger = new Logger("KeyMapForm");
	
	public KeyMapForm(KeyMap map) {
		super("Key Maps");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());
		
		//_worldList.setBorder(new LineBorder(Color.black));
        //_worldList.setSelectedIndex(0);
		//add(_worldList, BorderLayout.WEST);
		
		
		DefaultListModel model = new DefaultListModel();
		
		ArrayList<KeyMapping> mappings = map.getMappings();
		for (KeyMapping mapping: mappings) {
			int key = mapping.getKeyCode();
			KeyCode keyCode = new KeyCode(key);
			
			model.addElement(" " + keyCode.toString() + " ");
		}
		
		JList keyList = new JList(model);
		keyList.setBorder(new LineBorder(Color.black));
		keyList.setSelectedIndex(0);
		add(new JScrollPane(keyList), BorderLayout.LINE_START);
		
		JPanel settings = new JPanel();
		settings.setLayout(new GridFlowLayout(6, 4));
		settings.add(new JLabel("Key Code"), new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		JTextField codeBox = new JTextField(10);
		codeBox.setAlignmentX(CENTER_ALIGNMENT);
		settings.add(codeBox, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 2));
		
		settings.add(new JLabel("Macro to run"), new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		JComboBox macroList = new JComboBox();
		for (Macro macro: MacroManager.getMacroList()) {
			macroList.addItem(new ComboItem(macro.getName(), macro.getName()));
		}
		macroList.setEditable(true);
		settings.add(macroList, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 2));
		
		JButton saveButton = new JButton("Save");
		settings.add(saveButton, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));
		
		JButton cancelButton = new JButton("Cancel");
		settings.add(cancelButton, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 2));
		
		add(settings, BorderLayout.CENTER);
		
		setLocation(300, 300);
		pack();
		setResizable(false);
		setVisible(true);
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
	
	class buttonClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			_logger.logInfo("Button click: '" + cmd + "'");
			
			if (cmd.equalsIgnoreCase("Worlds")) {
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
				keyCode.ctrl(true);;
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
