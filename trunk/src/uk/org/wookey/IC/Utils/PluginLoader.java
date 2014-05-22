package uk.org.wookey.IC.Utils;

public class PluginLoader extends ClassLoader {
	public Class<?> defineClass(String name, byte[] b) {
		return defineClass(name, b, 0, b.length);
	}
}
