package uk.org.wookey.ICPlugin.MCP;

import uk.org.wookey.IC.Utils.ParserException;

public class MCPVersion {
	private int _major;
	private int _minor;
	
	public MCPVersion(String version) throws ParserException {
		set(version);
	}
	
	public void set(String version) throws ParserException {
		String parts[] = version.split("\\.");
		
		if (parts.length != 2) {
			throw new ParserException("Badly formed MCP version string: '" + version + "'.");
		}
		
		_major = Integer.parseInt(parts[0]);
		_minor = Integer.parseInt(parts[1]);
	}
	
	public String toString() {
		return new String(_major + "." + _minor);
	}
	
	public MCPVersion min(String otherString) throws ParserException {
		MCPVersion other = new MCPVersion(otherString);
		
		return min(other);
	}
	
	public MCPVersion min(MCPVersion other) {
		if (_major < other._major) {
			return this;
		}
		
		if (_major > other._major) {
			return other;
		}
		
		if (_minor > other._minor) {
			return other;
		}
		
		return this;
	}
}
