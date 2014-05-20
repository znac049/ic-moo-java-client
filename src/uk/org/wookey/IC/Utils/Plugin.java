package uk.org.wookey.IC.Utils;

import uk.org.wookey.IC.Interfaces.ICPluginInterface;

public class Plugin implements ICPluginInterface {
	private static Logger _logger = new Logger("Generic Plugin");

	public Plugin() {
	}
	
	@Override
	public void bimble() {
		_logger.logMsg("Hello from plugin");
	}

}
