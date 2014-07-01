package uk.org.wookey.IC.GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import uk.org.wookey.IC.Utils.HorizontalPanel;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.Macro;
import uk.org.wookey.IC.Utils.MacroManager;

public class MacroManagerForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private Logger _logger = new Logger("KeyMapForm");
	private JTextArea bindingPanel;
	
	public MacroManagerForm() {
		super("Macros");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setFocusable(true);
		requestFocusInWindow();

		getContentPane().setLayout(new GridBagLayout());
		layoutContents();
		
		setSize(500, 300);
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
		
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		
		gbc.insets = new Insets(4, 4, 4, 4);
		
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

		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		
		gbc.gridx = 1;
		gbc.gridheight = 1;

		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		bindingPanel = makeBindingPanel();
		add(bindingPanel, gbc);
		
		gbc.gridx = 2;
		gbc.anchor = GridBagConstraints.PAGE_START;
		JComboBox macroList = makeListOfMacros();
		add(macroList, gbc);
		
		JTextArea help = new JTextArea();
		help.setLineWrap(true);
		help.setText("Select a key combination from the list to the left " +
				"or select <New>. Type the key combination in the yellow " +
				"box and then select a macro from the dropdown or type in " +
				"the name of a new macro to create a new macro");
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
	
	private JTextArea makeBindingPanel() {
		JTextArea codeBox = new JTextArea();
		codeBox.setAlignmentX(CENTER_ALIGNMENT);
		codeBox.setBorder(new LineBorder(Color.black));
		codeBox.setPreferredSize(new Dimension(100, 100));
		codeBox.setBackground(new Color(240, 224, 64));
		codeBox.setBorder(new LineBorder(Color.black));
		codeBox.setLineWrap(true);
		codeBox.setEditable(false);
		
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
			}
			
		}		
	}
}
