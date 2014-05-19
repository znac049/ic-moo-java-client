package uk.org.wookey.IC.Utils;

public class Plugin implements Runnable {
	private static Logger _logger = new Logger("Generic Addin");

	public Plugin() {
	}
	
	protected void reportPluginDetails() {
		_logger.logMsg("Plugin type: " + getPluginName());
	}
	
	protected String getPluginName() {
		return getClass().getCanonicalName();
	}
	
	@Override
	public void run() {
	}

}
