package uk.org.wookey.IC.Utils;

public class PluginInfo {
	private Logger _logger = new Logger("PluginInfo");
	private String _fullName;
	private Class<?> _class;
	
	public PluginInfo(Class<?> classObj, String fullName) {
		_fullName = fullName;
		_class = classObj;
	}
	
	public Object newInstance() {
		try {
			return _class.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		_logger.logMsg("Failed to create a new instance of " + _fullName);
		return null;
	}
}
