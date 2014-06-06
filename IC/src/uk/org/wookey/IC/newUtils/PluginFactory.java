package uk.org.wookey.IC.newUtils;

import java.io.File;
import java.util.ArrayList;

import uk.org.wookey.IC.Utils.CorePlugin;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.PluginLoader;

public class PluginFactory {
	private static final Logger _logger = new Logger("PluginFactory");
	public static final String pluginDir = "./plugins";
	private static ArrayList<Class> plugins = null;
	
	public static void scanForPlugins() {
		if (plugins == null) {
			plugins = new ArrayList<Class>();
			File dir = new File(pluginDir);
			
			_logger.logMsg("Scanning for plugins");
			if (dir.exists()) {
				File files[] = dir.listFiles();
			
				for (File file : files) {
					if (file.isDirectory()) {
						loadPlugin(file);
					}
				}
			}
			else {
				_logger.logMsg("Plugin directory doesn't exist. Plugin scan aborted.");
			}
		}
		else {
			_logger.logMsg("Plugin scan only needs to happen once");
		}
	}
	
	public static ArrayList<CorePlugin> pluginsSupporting(CorePluginInterface.PluginType pluginType) {
		ArrayList<CorePlugin> supportingPlugins = new ArrayList<CorePlugin>();
		
		if (plugins == null) {
			PluginFactory.scanForPlugins();
		}
		
		if (plugins == null) {
			_logger.logWarn("No plugins to check against in pluginsSupporting()");
		}
		
		for (Class plugin: plugins) {
			CorePlugin p;
			try {
				p = (CorePlugin) plugin.newInstance();

				if (p.activate()) {
					_logger.logSuccess("Plugin " + p.getName() + " instance activated");
					
					if (p.supports(pluginType)) {
						supportingPlugins.add(p);
					}
				}
				else {
					_logger.logError("Failed to activate plugin " + p.getName() + " instance");
				}
			} catch (InstantiationException | IllegalAccessException e) {
				_logger.logError("Failed to create instance of a plugin");
			}
		}
		
		return supportingPlugins;
	}
	
	private static void loadPlugin(File dir) {
		_logger.logMsg("Plugin directory: " + dir.getName());
		PluginLoader loader = new PluginLoader();
		
		String packageName = dir.getName();

		int dot = packageName.lastIndexOf('.');
		String className = packageName.substring(dot+1);
		
		Class<?> c = null;
		
		c = loader.findClass(packageName + '.' + className);
		if (isPlugin(c)) {
			_logger.logMsg(className + " is a plugin");
			
			plugins.add(c);
			_logger.logMsg(" Added to plugin list");
		}
	}
		
	private static boolean isPlugin(Class<?> c) {
		//Method[] methods = c.getDeclaredMethods();
		
		Object p;
		try {
			p = (Object) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return false;
		}
		
		return (p instanceof CorePluginInterface);
	}
}
