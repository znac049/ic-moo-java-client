package uk.org.wookey.IC.newUtils;

import java.io.File;
import java.util.ArrayList;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.PluginLoader;

public class PluginFactory {
	private static final Logger _logger = new Logger("PluginFactory");
	//private static String[] requiredMethods = {"energizePlugin"};
	public static final String pluginDir = "./plugins";
	private static ArrayList<CorePluginInterface> plugins = null;
	
	public static void scanForPlugins() {
		if (plugins == null) {
			plugins = new ArrayList<CorePluginInterface>();
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
			// Say hello to the plugin class
			try {
				plugins.add((CorePluginInterface) c.newInstance());
				_logger.logMsg("Added to plugin list");
				
			} catch (InstantiationException
					| IllegalAccessException e) {
				_logger.printBacktrace("Failed to add to plugin list", e);
				e.printStackTrace();
			}			
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
		
		
		//for (String required : requiredMethods) {
		//	boolean found = false;
			
		//	for (Method method: methods) {
		//		if (required.equals(method.getName())) {
		//			found = true;
		//		}				
		//	}
			
		//	if (!found) {
		//		return false;
		//	}
		//}
		
		//return true;
	}
}
