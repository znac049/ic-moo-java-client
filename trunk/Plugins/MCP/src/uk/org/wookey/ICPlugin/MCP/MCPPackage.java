package uk.org.wookey.ICPlugin.MCP;

import uk.org.wookey.IC.Utils.ParserException;


public class MCPPackage {
	private String _name;
	private MCPVersion _min;
	private MCPVersion _max;
	
	public MCPPackage(String name, String min, String max) {
		_name = name;
		try {
			_min = new MCPVersion(min);
			_max = new MCPVersion(max);
		} catch (ParserException e) {
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
