package uk.org.wookey.IC.GUI;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ConfigPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public ConfigPanel(String name) {
		super();
		
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(new GridLayout(0, 1));
		
		setBorder(BorderFactory.createTitledBorder(name));
	}
	
	public void addItem(String label, JComponent comp) {
		FormItem item = new FormItem(label, comp);
		
		add(item);
	}
	
	public void addItem(JComponent comp) {
		addItem("", comp);
	}
}
