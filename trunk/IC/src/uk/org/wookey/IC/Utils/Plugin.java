package uk.org.wookey.IC.Utils;

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
		
		return true;
	}

	public JPanel getWorldSettingsTab() {
		JPanel panel = new JPanel();
		
		panel.setLayout(null);
		
		JLabel title = new JLabel(_name + " Settings");
		panel.add(title);
		
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
