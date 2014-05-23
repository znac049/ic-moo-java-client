package uk.org.wookey.IC.MCP;


public class MCPPackage {
	private String _name;
	private MCPVersion _min;
	private MCPVersion _max;
	
	public MCPPackage(String name, String min, String max) {
		_name = name;
		try {
			_min = new MCPVersion(min);
			_max = new MCPVersion(max);
		} catch (MCPException e) {
			_min = null;
			_max = null;
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return _name;
	}
	
	public MCPVersion getMin() {
		return _min;
	}
	
	public MCPVersion getMax() {
		return _max;
	}
}
