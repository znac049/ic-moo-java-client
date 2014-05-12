package uk.org.wookey.IC.Utils;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class WorldDetail {
	private String worldName;
	private String server;
	private int port;
	private boolean localEcho;
	private boolean mcpEnabled;
	private boolean autoConnect;
	private boolean autoLogin;
	private String userName;
	private String userPassword;
	private Preferences world;
	
	public WorldDetail(String name) {
		Preferences worlds = Preferences.userRoot().node("uk/org/wookey/IC/worlds");
		try {
			if (worlds.nodeExists(name)) {
				world = worlds.node(name);
				
				worldName = name;
				server = world.get("Server", "");
				port = world.getInt("Port", -1);
				autoConnect = world.getBoolean("Autoconnect", false);
				
				localEcho = world.getBoolean("LocalEcho", true);
				mcpEnabled = world.getBoolean("MCPEnabled", true);
				
				autoLogin = world.getBoolean("Autologin", true);
				userName = world.get("UserName", "");
				userPassword = world.get("Password", "");
			}
			else {
				world = worlds.node(name);
				worldName = name;
				
				server = world.get("Server", "");
				port = world.getInt("Port", -1);
				autoConnect = world.getBoolean("Autoconnect", false);
				
				localEcho = world.getBoolean("LocalEcho", true);
				mcpEnabled = world.getBoolean("MCPEnabled", true);
				
				autoLogin = world.getBoolean("Autologin", true);
				userName = world.get("UserName", "");
				userPassword = world.get("Password", "");
			}
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getWorldName() {
		return worldName;
	}
	
	public void setWorldName(String name) {
		try {
			world.sync();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		world = Preferences.userRoot().node("uk/org/wookey/IC/worlds/" + name);
		worldName = name;
	}
	
	public String getServerName() {
		return server;
	}
	
	public void setServerName(String name) {
		world.put("Server", name);
		server = name;
	}
	
	public int getServerPort() {
		return port;
	}
	
	public void setServerPort(int num) {
		world.putInt("Port", num);
		port = num;
	}
	
	public boolean getAutoConnect() {
		return autoConnect;
	}
	
	public void setAutoConnect(boolean value) {
		world.putBoolean("Autoconnect", value);
		autoConnect = value;
	}

	public boolean getLocalEcho() {
		return localEcho;
	}
	
	public void setLocalEcho(boolean value) {
		world.putBoolean("LocalEcho", value);
		autoConnect = value;
	}

	public boolean getMCPEnabled() {
		return mcpEnabled;
	}
	
	public void setMCPEnabled(boolean value) {
		world.putBoolean("MCPEnabled", value);
		mcpEnabled = value;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String name) {
		world.put("UserName", name);
		userName = name;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public void setUserPassword(String pass) {
		world.put("Password", pass);
		userPassword = pass;
	}
	
	public boolean getAutoLogin() {
		return autoLogin;
	}
	
	public void setAutoLogin(boolean value) {
		world.putBoolean("Autologin", value);
		autoLogin = value;
	}
}
