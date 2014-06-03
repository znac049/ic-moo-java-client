package uk.org.wookey.IC.Factories;

import java.io.File;
import java.lang.reflect.Method;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.Plugin;
import uk.org.wookey.IC.Utils.PluginLoader;

public class PluginFactory {
	private static final Logger _logger = new Logger("PluginFactory");
	private static String[] requiredMethods = {"energizePlugin"};
	public static final String pluginDir = "./plugins";
	
	public static void scanForPlugins() {
		File dir = new File(pluginDir);
		
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
	
	private static void loadPlugin(File dir) {
		_logger.logMsg("Plugin directory: " + dir.getAbsolutePath());
		PluginLoader loader = new PluginLoader();
		
		String packageName = dir.getName();

		int dot = packageName.lastIndexOf('.');
		String className = packageName.substring(dot+1);
		
		Class<?> c = null;
		Plugin plugin = null;
		
		c = loader.findClass(packageName + '.' + className);
		if (isPlugin(c)) {
		
			// Say hello to the plugin class
			try {
				plugin = (Plugin) c.newInstance();
				
				if (!plugin.energizePlugin()) {
					_logger.logMsg("Energize FAILED");
				}
				else {
					_logger.logMsg("Plugin energised Ok");
				}
			} catch (InstantiationException
					| IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
		
	private static String makeClassname(File dir, String className) {
		String packageName = dir.getName();

		return packageName + "." + className;
	}
	
	private static boolean isPlugin(Class<?> c) {
		Method[] methods = c.getDeclaredMethods();
		
		for (String required : requiredMethods) {
			boolean found = false;
			
			for (Method method: methods) {
				if (required.equals(method.getName())) {
					found = true;
				}				
			}
			
			if (!found) {
				return false;
			}
		}
		
		return true;
	}
}
