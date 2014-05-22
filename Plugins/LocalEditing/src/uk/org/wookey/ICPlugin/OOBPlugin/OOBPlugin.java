package uk.org.wookey.ICPlugin.OOBPlugin;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.Plugin;;

public class OOBPlugin extends Plugin {
	public final Logger _logger = new Logger("OOBPlugin");
	
	@Override
	public boolean energizePlugin() {
		_logger.logMsg("Hello from the plugin");
		
		return true;
	}
	
	@Override
	public boolean handlesOOB() {
		_logger.logMsg("handlesOOB(); -> true");
		return true;
	}
}