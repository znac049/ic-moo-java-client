package uk.org.wookey.IC.newGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.border.LineBorder;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.newUtils.HorizontalPanel;
import uk.org.wookey.IC.newUtils.KeyCode;
import uk.org.wookey.IC.newUtils.KeyMap;
import uk.org.wookey.IC.newUtils.KeyMapping;
import uk.org.wookey.IC.newUtils.Macro;
import uk.org.wookey.IC.newUtils.MacroManager;
import uk.org.wookey.IC.newUtils.VerticalPanel;
import webBoltOns.layoutManager.GridFlowLayout;
import webBoltOns.layoutManager.GridFlowLayoutParameter;
import java.awt.FlowLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class KeyMapForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private Logger _logger = new Logger("KeyMapForm");
	
	private JList keyList;
	
	public KeyMapForm(KeyMap map) {
		super("Key Maps");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(new KeyHandler());

		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(makeListOfBindings(map), BorderLayout.LINE_START);
		
		getContentPane().add(makeBindingPanel(), BorderLayout.CENTER);
		
		getContentPane().add(makeButtonsPanel(), BorderLayout.PAGE_END);
		
		setLocation(300, 300);
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	private JPanel makeListOfBindings(KeyMap map) {
		VerticalPanel panel = new VerticalPanel();

		panel.add(new JLabel("Current Bindings"));

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
		panel.add(new JScrollPane(keyList));
				
		return panel;
	}
	
	private JPanel makeBindingPanel() {
		VerticalPanel panel = new VerticalPanel();
		
		JLabel lab = new JLabel("Key Code");
		lab.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(lab);

		JPanel codeBox = new JPanel();
		codeBox.setAlignmentX(CENTER_ALIGNMENT);
		codeBox.setBorder(new LineBorder(Color.black));
		codeBox.setPreferredSize(new Dimension(100, 100));
		panel.add(codeBox);
		codeBox.setLayout(new FormLayout(new ColumnSpec[] {},
			new RowSpec[] {}));
		
		lab = new JLabel("Macro");
		lab.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(lab);

		JComboBox macroList = new JComboBox();
		macroList.setAlignmentX(CENTER_ALIGNMENT);
		for (Macro macro: MacroManager.getMacroList()) {
			macroList.addItem(new ComboItem(macro.getName(), macro.getName()));
		}
		macroList.setEditable(true);
		panel.add(macroList);
		
		JLabel help = new JLabel();
		help.setAlignmentX(CENTER_ALIGNMENT);
		help.setBorder(new LineBorder(Color.black));
		help.setPreferredSize(new Dimension(250, 100));
		help.setText("Select a macro to bind to or type the name of a new macro");
		panel.add(help);

		return panel;
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
