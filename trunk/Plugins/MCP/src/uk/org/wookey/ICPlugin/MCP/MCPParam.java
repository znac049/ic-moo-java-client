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
	
	public String getValueQuoteSafe() {
		if (_value.indexOf(' ') >= 0) {
			return '"' + _value + '"';
		}
		
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
	
	public boolean requiresMultiline() {
		if (_key.length() == 0) {
			return false;
		}
		
		if (_key.charAt(_key.length() - 1) == '*') {
			return true;
		}
		
		return false;
	}
}
