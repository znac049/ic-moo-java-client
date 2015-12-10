package uk.org.wookey.IC.GUI;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import uk.org.wookey.IC.GUI.Forms.ConfigPanel;

public class GlobalConfigPanel extends ConfigPanel {
	private static final long serialVersionUID = 1L;

	public GlobalConfigPanel(String name) {
		super(name);
		
		add(new JCheckBox("Plugin Enabled"));
	}
}
