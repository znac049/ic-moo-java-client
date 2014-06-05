package uk.org.wookey.IC.Utils;

import uk.org.wookey.IC.newGUI.WorldTab;
import uk.org.wookey.IC.newUtils.Char;
import uk.org.wookey.IC.newUtils.IOPluginInterface;
import uk.org.wookey.IC.newUtils.Line;

public class IOPlugin extends CorePlugin implements IOPluginInterface {
	//private Logger _logger = new Logger("Base Plugin");
	protected WorldTab _worldTab = null;
	protected String _worldName = null;
	protected boolean _enabled = false; // Disabled by default
	
	public IOPlugin() {
		setName("*noname*");
	}

	@Override
	public Status remoteCharIn(Char c) {
		return IOPluginInterface.Status.IGNORED;
	}

	@Override
	public Status remoteLineIn(Line line) {
		return IOPluginInterface.Status.IGNORED;
	}

	@Override
	public Status transformRemoteInputLine() {
		return IOPluginInterface.Status.IGNORED;
	}
	
}
