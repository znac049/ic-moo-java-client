package uk.org.wookey.ICPlugin.MCP;

public class MCPParam {
	private String _key;
	private String _value;
	
	public MCPParam() {
		this("", "");
	}

	public MCPParam(String key, String value) {
		if (key.endsWith(":")) {
			_key = key.substring(0, key.length()-1);
		}
		else {
			_key = key;
		}
		
		_value = value;
	}
	
	public String getKey() {
		return _key;
	}
	
	public String getValue() {
		return _value;
	}
	
	public void setKey(String key) {
		if (key.endsWith(":")) {
			_key = key.substring(0, key.length()-1);
		}
		else {
			_key = key;
		}
	}
	
	public void setValue(String value) {
		_value = value;
	}
	
	public void appendValue(String extra) {
		_value += extra;
	}
}
