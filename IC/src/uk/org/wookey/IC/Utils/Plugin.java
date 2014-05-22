package uk.org.wookey.IC.Utils;

import uk.org.wookey.IC.Interfaces.PluginInterface;

public class Plugin implements PluginInterface {
	private static Logger _logger = new Logger("Generic Plugin");

	public Plugin() {
	}

	@Override
	public boolean energizePlugin() {
		// Called by the PluginFactory as the first step in getting to know a plugin
		
		return false;
	}
	
	@Override
	public boolean handlesOOB() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Peter";
	}
}
