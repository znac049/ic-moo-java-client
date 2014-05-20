package uk.org.wookey.IC.Factories;

import java.io.File;
import Plugins.PluginLoader;
import uk.org.wookey.IC.Utils.AssociativeArray;
import uk.org.wookey.IC.Utils.Logger;

public class PluginFactory {
	private static final Logger _logger = new Logger("PluginFactory");
	private static AssociativeArray plugins = new AssociativeArray();
	
	public static void scanForPlugins() {
		String pluginDir = "./plugins";
		File dir = new File(pluginDir);
		
		_logger.logMsg("scanForPlugins");
		
		if (dir.exists()) {
			_logger.logMsg("Reading directory");
			
			PluginLoader loader = new PluginLoader(dir);
			File files[] = dir.listFiles();
		
			for (File file : files) {
				String className = file.getName();
				
				if (className.endsWith(".class")) {
					className = className.substring(0, className.indexOf(".class"));
					
					_logger.logMsg("Found candidate plugin file: '" + file.getName() + "' for class '" + className + "'");
				
					try {
						Class instance = loader.loadClass(className);
						_logger.logMsg("Class " + className + "' loaded ok");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else {
			_logger.logMsg("Plugin directory doesn't exist. Plugin scan aborted.");
		}
	}
}
