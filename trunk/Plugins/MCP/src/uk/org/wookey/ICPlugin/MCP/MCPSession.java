package uk.org.wookey.ICPlugin.MCP;

import java.util.prefs.Preferences;

public class MCPSession {
	private long _sessionId;
	
	public MCPSession() {
		Preferences prefs = Preferences.userRoot().node("uk/org/wookey/IC/MCP");

		_sessionId = prefs.getLong("SessionCounter", 1);
		prefs.putLong("SessionCounter", _sessionId+1);
	}
	
	public void dump() {
		System.out.println("Session Id: " + _sessionId);
	}
	
	public String getSessionKey() {
		return "IC-" + Long.toHexString(_sessionId);
	}
}
