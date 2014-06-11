package uk.org.wookey.IC.Utils;


public class CorePlugin implements CorePluginInterface {
	private Logger _logger = new Logger("CorePlugin");
	protected String name;
	
	public CorePlugin() {
		name = null;
	}
	
	@Override
	public boolean supports(PluginType t) {
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String n) {
		name = n;
		_logger = new Logger(name);
	}

	@Override
	public boolean activate() {
		_logger.logInfo("Activating Core plugin " + getName());
		
		return true;
	}

}
