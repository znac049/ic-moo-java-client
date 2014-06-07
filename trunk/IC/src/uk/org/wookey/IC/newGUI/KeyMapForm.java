package uk.org.wookey.IC.newGUI;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.org.wookey.IC.newUtils.KeyCode;
import uk.org.wookey.IC.newUtils.KeyMap;
import uk.org.wookey.IC.newUtils.KeyMapping;

public class KeyMapForm extends JFrame {
	private static final long serialVersionUID = 1L;
	
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
			
			model.addElement(keyCode.toString());
		}
		
		JList keyList = new JList(model);
		keyList.setSelectedIndex(0);
		add(keyList, BorderLayout.LINE_START);
		
		JPanel settings = new JPanel();
		settings.add(new JLabel("Key Code"));
		
		JTextField codeBox = new JTextField(10);
		settings.add(codeBox);
		
		settings.add(new JLabel("Macro to run"));
		
		JComboBox macroList = new JComboBox();
		macroList.addItem(new ComboItem("One", "En"));
		macroList.addItem(new ComboItem("Two", "To"));
		macroList.addItem(new ComboItem("Three", "Tre"));
		macroList.addItem(new ComboItem("Four", "Fire"));
		macroList.setEditable(true);
		settings.add(macroList);
		
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
}
