package uk.org.wookey.IC.Factories;

import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import uk.org.wookey.IC.Utils.AssociativeArray;
import uk.org.wookey.IC.Utils.FileExtensionFilter;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.Plugin;
import uk.org.wookey.IC.Utils.PluginInfo;
import uk.org.wookey.IC.Utils.PluginLoader;

public class PluginFactory {
	private static final Logger _logger = new Logger("PluginFactory");
	private static ArrayList<PluginInfo> plugins = new ArrayList<PluginInfo>();
	private static String[] requiredMethods = {"energizePlugin"};
	
	public static void scanForPlugins() {
		String pluginDir = "./plugins";
		File dir = new File(pluginDir);
		
		_logger.logMsg("scanForPlugins");
		
		if (dir.exists()) {
			_logger.logMsg("Reading directory");
			
			PluginLoader loader = new PluginLoader();
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
		//ClassLoader loader = PluginFactory.class.getClassLoader();
		PluginLoader loader = new PluginLoader();

		FileExtensionFilter filter = new FileExtensionFilter(".class");
		File files[] = dir.listFiles((FilenameFilter) filter);
		
		Class<?> c;
		Class<?> pluginClass = null;
		Plugin plugin = null;
		String pluginName = null;
		
		for (File file : files) {
			_logger.logMsg("Found: " + file.getName());
			
			byte[] code = fetchBytecode(file);
			c = loader.defineClass(makeClassname(dir, file), code);

			if (isPlugin(c)) {
				pluginClass = c;
				pluginName = c.getName();
			}
		}
		
		// Say hello to the plugin class
		if (pluginClass != null) {
			try {
				plugin = (Plugin) pluginClass.newInstance();
				
				_logger.logMsg("Energizing plugin");
				
				if (!plugin.energizePlugin()) {
					_logger.logMsg("Energize FAILED");
				}
				else {
					_logger.logMsg("Energized OK");
					
					plugins.add(new PluginInfo(pluginClass, makeClassname(dir, pluginName)));
				}
			} catch (InstantiationException
					| IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
		
	private static byte[] fetchBytecode(File classFile) {
		if (classFile.exists()) {
			FileInputStream in;
			try {
				in = new FileInputStream(classFile);

				byte[] code = new byte[in.available()];
				in.read(code);
				in.close();
				
				_logger.logMsg("Read " + code.length + " bytes");
				
				return code;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	private static String makeClassname(File dir, String className) {
		String packageName = dir.getName();

		return packageName + "." + className;
	}
	
	private static String makeClassname(File dir, File file) {
		String className = file.getName();
		
		// Remove file suffix from file
		if (className.endsWith(".class")) {
			className = className.substring(0, className.indexOf(".class"));
		}
		
		return makeClassname(dir, className);
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
	
	public static void populateSettingsTabs(JTabbedPane tabs) {
		JComponent tab1 = makeTextPanel("Plugin 1");
		tabs.addTab("Tab 1", null, tab1, "Does twice as much nothing");
	}
	
	
	protected static JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
}
