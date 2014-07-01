package uk.org.wookey.IC.Utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class PluginManager {
	private static final Logger _logger = new Logger("PluginManager");
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
					else if ((file.isFile()) && (file.getName().endsWith(".jar"))) {
						_logger.logInfo("Found a plugin in a JAR file!!!!");
						loadPluginFromJAR(file);
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
			PluginManager.scanForPlugins();
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
		String packageName = dir.getName();

		int dot = packageName.lastIndexOf('.');
		String className = packageName;
		
		if (dot != -1) {
			className = packageName.substring(dot+1);
		}
		
		loadPluginFromURL("file:" + dir.getAbsolutePath() + "/", packageName + "." + className);
	}
		
	private static void loadPluginFromJAR(File jar) {
		String packageName = jar.getName();
		
		if (!packageName.endsWith(".jar")) {
			_logger.logError("Trying to load a plugin from a jar file but the file extension id NOT '.jar'!");
			return;
		}

		// remove the '.jar' extension
		packageName = packageName.substring(0, packageName.length() -  4);
		
		String className = packageName;
		
		int dot = packageName.lastIndexOf('.');
		if (dot != -1) {
			className = packageName.substring(dot+1);
		}

		loadPluginFromURL("jar:file:" + jar.getAbsolutePath()+"!/", packageName + "." + className);	        
	}
	
	private static void loadPluginFromURL(String url, String className) {		
		try {
			URL[] urls = { new URL(url) };
			URLClassLoader ucl = URLClassLoader.newInstance(urls);
			
			_logger.logInfo("Loading plugin '" + className + "' from URL " + URLString(ucl));
			
			Class<?> c = ucl.loadClass(className);
    		if (isPlugin(c)) {
    			_logger.logMsg(className + " is a plugin");
    			
    			plugins.add(c);
    		}
		} catch (ClassNotFoundException e) {
			_logger.logError("Failed to load plugin using URLClassLoader!");
		} catch (MalformedURLException e) {
			_logger.logError("Problem with url '" + url + "' in loadPluginFromURL()");
		}
	}
		
	private static String URLString(URLClassLoader ucl) {
		URL[] urls = ucl.getURLs();
		
		if (urls.length != 1) {
			return "multiple URLS unexpected";
		}
		
		return urls[0].toExternalForm();	
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
