package uk.org.wookey.IC.MCP;

public class MCPVersion {
	private int _major;
	private int _minor;
	
	public MCPVersion(String version) throws MCPException {
		set(version);
	}
	
	public void set(String version) throws MCPException {
		String parts[] = version.split("\\.");
		
		if (parts.length != 2) {
			throw new MCPException("Badly formed MCP version string: '" + version + "'.");
		}
		
		_major = Integer.parseInt(parts[0]);
		_minor = Integer.parseInt(parts[1]);
	}
	
	public String toString() {
		return new String(_major + "." + _minor);
	}
	
	public MCPVersion min(String otherString) throws MCPException {
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
