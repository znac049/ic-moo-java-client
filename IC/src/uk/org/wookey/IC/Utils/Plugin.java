package uk.org.wookey.IC.Utils;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.org.wookey.IC.Tabs.WorldTab;

public class Plugin {
	private Logger _logger = new Logger("Base Plugin");
	private String _name;
	protected WorldTab _worldTab = null;

	public static final int Handled = 1;
	public static final int HandledFinal = 2;
	public static final int NotInterested = 3;
	
	public Plugin() {
		setName("*noname*");
	}
	
	public String getName() {
		return _name;
	}
	
	protected void setName(String name) {
		_name = name;
	}
	
	public boolean energizePlugin() {
		setName("Base Plugin");
		
		return true;
	}
	
	public boolean connectTo(WorldTab worldTab) {
		_worldTab = worldTab;
		
		if (energizePlugin()) {
			return onConnect();
		}
		
		return false;
	}

	public boolean onConnect() {
		return true;
	}

	public JPanel getWorldSettingsTab() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel(_name + " Settings");
		panel.add(title);

		JCheckBox enabled = new JCheckBox("Plugin enabled");
		panel.add(enabled);
		
		return panel;
	}

	public boolean handlesRemoteLineInput() {
		return false;
	}
	
	public boolean handlesRemoteLineOutput() {
		return false;
	}
	
	public int handleRemoteLineInput(String line) {
		return NotInterested;
	}
}
