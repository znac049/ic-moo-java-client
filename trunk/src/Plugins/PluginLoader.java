package Plugins;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import uk.org.wookey.IC.Utils.Logger;

public class PluginLoader extends ClassLoader {
	private static Logger _logger = new Logger("PluginLoader", 9);
	private File _rootDir;
	
	public PluginLoader(File rootDir) {
		super();
		_rootDir = rootDir;
	}
	
	public Class<?> findClass(String name) {
		Class<?> c = null;
		String fullName = name;
		
		if (!name.endsWith(".class")) {
			name += ".class";
		}
		
		_logger.logMsg("findClass: looking for '" + name + " file to load");
		
		File f = new File(_rootDir, name);
		if (f.exists()) {
			_logger.logMsg("Found class file!");
			try {
				FileInputStream in = new FileInputStream(f);
				_logger.logMsg("Reading " + in.available() + " bytes from the class file");
				
				byte[] code = new byte[in.available()];
				in.read(code);
				in.close();
				
				_logger.logMsg("Read " + code.length + " bytes");
				
				_logger.logMsg("Full name: '" + fullName + "'");
				c = defineClass(fullName, code, 0, code.length);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return c;
	}
}