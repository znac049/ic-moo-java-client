package uk.org.wookey.IC.Factories;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JTabbedPane;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.Plugin;
import uk.org.wookey.IC.Utils.PluginInfo;
import uk.org.wookey.IC.Utils.PluginLoader;

public class PluginFactory {
	private static final Logger _logger = new Logger("PluginFactory");
	private static String[] requiredMethods = {"energizePlugin"};
	public static final String pluginDir = "./plugins";
	private static ArrayList<PluginInfo> plugins = new ArrayList<PluginInfo>();
	
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
		String pluginName = null;
		
		c = loader.findClass(packageName + '.' + className);
		if (isPlugin(c)) {
		
			// Say hello to the plugin class
			try {
				plugin = (Plugin) c.newInstance();
				
				if (!plugin.energizePlugin()) {
					_logger.logMsg("Energize FAILED");
				}
				else {
					plugins.add(new PluginInfo(c, makeClassname(dir, pluginName)));
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
	
	public static boolean populateSettingsTabs(JTabbedPane tabs) {
		for (PluginInfo info: plugins) {
			Plugin plugin = (Plugin) info.newInstance();
			
			if (plugin.energizePlugin()) {
				tabs.addTab(plugin.getName(), plugin.getWorldSettingsTab());
			}
		}
		
		return (plugins.size() > 0);
	}
	
	public static ArrayList<Plugin> getRemoteInputPlugins() {
		ArrayList<Plugin> plugins = new ArrayList<Plugin>();
		
		_logger.logMsg("Looking for plugins that handle remote line input");
		_logger.logMsg("total number of plugins found: " + plugins.size());
		for (PluginInfo info: PluginFactory.plugins) {
			Plugin plugin = (Plugin) info.newInstance();
			
			// Does the plugin handle remote line input?
			if (plugin.handlesRemoteLineInput() && plugin.energizePlugin()) {
				_logger.logMsg("  plugin '" + plugin.getName() + "' handles remote line input");
				plugins.add(plugin);
			}
		}
		
		return plugins;
	}
}
