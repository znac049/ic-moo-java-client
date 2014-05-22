package uk.org.wookey.ICPlugin.LocalEditing;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.Plugin;;

public class LocalEditing extends Plugin {
	public final Logger _logger = new Logger("LocalEditing");
	
	@Override
	public boolean energizePlugin() {
		_logger.logMsg("Hello from the local editing plugin");
		
		return true;
	}
	
	@Override
	public boolean handlesOOB() {
		_logger.logMsg("handlesOOB(); -> true");
		return true;
	}
}