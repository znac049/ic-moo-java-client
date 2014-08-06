package uk.org.wookey.IC.GUI;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ConfigPanel extends JPanel {
	public ConfigPanel(String name) {
		super();
		
		setBorder(BorderFactory.createTitledBorder(name));
	}
}
