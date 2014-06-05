package uk.org.wookey.IC.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import uk.org.wookey.IC.newUtils.PluginFactory;

public class PluginLoader extends ClassLoader {
	//private Logger _logger = new Logger("PluginLoader");

	public Class<?> defineClass(String name, byte[] b) {
		return defineClass(name, b, 0, b.length);
	}
	
	public Class<?> findClass(String name) {
		int dot = name.lastIndexOf('.');
		String pluginName = name.substring(0, dot);
		String className = name.substring(dot+1);
		
		File file = new File(PluginFactory.pluginDir + File.separator + pluginName + File.separator + className + ".class");
		
		if (file.exists()) {
			Class<?> c = null;
			byte[] byteCode = fetchBytecode(file);
			
			c = defineClass(name, byteCode);
			
			return c;
		}
		
		return null;		
	}
	
	private static byte[] fetchBytecode(File classFile) {
		if (classFile.exists()) {
			FileInputStream in;
			try {
				in = new FileInputStream(classFile);

				byte[] code = new byte[in.available()];
				in.read(code);
				in.close();
				
				return code;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
