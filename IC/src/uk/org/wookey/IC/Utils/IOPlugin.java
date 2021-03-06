package uk.org.wookey.IC.Utils;

import uk.org.wookey.IC.GUI.GlobalConfigPanel;
import uk.org.wookey.IC.GUI.WorldTab;

public class IOPlugin extends CorePlugin implements IOPluginInterface {
	private Logger _logger = new Logger("Base Plugin");
	protected String _worldName = null;
	protected boolean _enabled = false; // Disabled by default
	
	protected WorldTab worldTab = null;
	protected ServerConnection server = null;
	
	public boolean activate() {
		setName("IOPlugin");
		
		return true;
	}

	@Override
	public Status remoteLineIn(Line line) {
		return IOPluginInterface.Status.IGNORED;
	}

	@Override
	public boolean attach(ServerConnection serverPort, WorldTab tab) {
		server = serverPort;
		worldTab = tab;
		
		return true;
	}
	
	@Override
	public WorldTab getWorldTab() {
		return worldTab;
	}

	@Override
	public String remoteLinePeek(String lineSoFar) {
		return lineSoFar;
	}
}
