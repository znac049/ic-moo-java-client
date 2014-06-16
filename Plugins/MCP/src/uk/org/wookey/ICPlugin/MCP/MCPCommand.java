package uk.org.wookey.ICPlugin.MCP;

import java.util.ArrayList;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.ServerPort;

public class MCPCommand {
	private String _line;
	private String _name;
	private String _key;
	private ArrayList<MCPParam> _params;
	private final Logger _logger = new Logger("MCPCommand");
	
	public MCPCommand() {
		_line = null;
		_name = "mcp-nopackage";
		_key = null;
		_params = new ArrayList<MCPParam>();
	}
	
	public void parseLine(String line) throws ParserException {
		_line = line;
		
		MCPStringParser parser = new MCPStringParser(line);
		String next;
		try {
			_name = parser.nextItem();
			//_logger.logInfo("Name=" + _name);

			next = parser.nextItem();
		} catch (uk.org.wookey.IC.Utils.ParserException e) {
			e.printStackTrace();
			return;
		}
		
		if (_name.equals("*")) {
			// Multilines are special - ie nothing is escaped
			_key = next;
			
			next = parser.nextItem();
			String value = parser.getRemainingLine();
			//_logger.logMsg("MLItem='" + next + "', Value='" + value + "'");
			
			_params.add(new MCPParam(next, value));
		}
		else {
			if (next.lastIndexOf(':') != next.length()-1) {
				_key = next;
				//_logger.logMsg("Key='" + _key + "'");
				next = parser.nextItem();
			}
			
			while (!next.equals("")) {
				String value = parser.nextItem();
				//_logger.logMsg("Item='" + next + "', Value='" + value + "'");
				_params.add(new MCPParam(next, value));
				next = parser.nextItem();
			}
		}
	}
	
	public String getName() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public void setName(String name, String cmd) {
		_name = name + '-' + cmd;
	}
	
	public String getLine() {
		return _line;
	}
	
	public String getParam(String name) {
		if (name.endsWith(":")) {
			name = name.substring(0, name.length()-1);
		}
		
		for (int i=0; i<_params.size(); i++) {
			MCPParam para = _params.get(i);
			if (para.getKey().equalsIgnoreCase(name)) {
				return para.getValue();
			}
		}
		return null;
	}
	
	public String getParam(int idx) {
		if ((idx < 0) | (idx >= _params.size())) {
			_logger.logError("Parameter index out of range: " + idx);
			return null;
		}
		
		return _params.get(idx).getValue();
	}
	
	public String getParamName(int idx) {
		if ((idx < 0) | (idx >= _params.size())) {
			_logger.logError("Parameter index out of range: " + idx);
			return null;
		}
		
		return _params.get(idx).getKey();
	}
	
	public ArrayList<MCPParam> getParams() {
		return _params;
	}
	
	public void appendParam(String name, String extraText) {
		if (name.endsWith(":")) {
			name = name.substring(0, name.length()-1);
		}
		
		//_logger.logMsg("Append to '" + name + "': '" + extraText + "'");
		for (int i=0; i<_params.size(); i++) {
			MCPParam para = _params.get(i);
			//_logger.logMsg("Compare with '" + para.getKey() + "'");
			if (para.getKey().equalsIgnoreCase(name)) {
				para.appendValue(extraText);
				return;
			}
		}
		
		// No such param. Create and initialise one
		_params.add(new MCPParam(name, extraText));
	}
	
	public boolean isMultiLine() {
		for (int i=0; i<_params.size(); i++) {
			String paramName = _params.get(i).getKey();
			if (paramName.endsWith("*")) {
				//_logger.logMsg("Param '" + paramName + "' is still incomplete");
				return true;
			}
		}
		
		return false;
	}
	
	public String getAuthKey() {
		return _key;
	}
	
	public void setAuthKey(String key) {
		_key = key;
	}

	public void clearMultiline() {
		for (int i=0; i<_params.size(); i++) {
			MCPParam param = _params.get(i);
			String paramName = param.getKey();
			if (paramName.endsWith("*")) {
				param.setKey(paramName.substring(0, paramName.length()-1));
			}
		}
	}
	
	public void sendToServer(ServerPort server) {
		String line = "#$#" + getName() + " " + _key;
		boolean multiline = false;
		
		for (MCPParam para: _params) {
			if (para.requiresMultiline()) {
				line = line + " " + para.getKey() + ": \"\"";
				multiline = true;
			}
			else {
				line = line + " " + para.getKey() + ": " + para.getValueQuoteSafe();
			}
		}
		
		if (!multiline) {
			server.writeLine(line);
		}
		else {
			String sessionKey = new MCPSession().getSessionKey();

			server.writeLine(line + " _data-tag: " + sessionKey);
			
			for (MCPParam para: _params) {
				if (para.requiresMultiline()) {
					String key = para.getKey();
					key = key.substring(0, key.length() - 1);

					String prefix = "#$#* " + sessionKey + " " + key + ": ";
					
					// Now send each line as a continuation
					String lines[] = para.getValue().split("\n");
					for (int i=0; i<lines.length; i++) {
						server.writeLine(prefix + lines[i]);
					}
				}
			}
				
			server.writeLine("#$#: " + sessionKey);
		}
	}

	public void addParam(String key, String value) {
		if (key.endsWith(":")) {
			key = key.substring(0, key.length()-1);
		}
		
		_params.add(new MCPParam(key, value));
	}
	
	public int getParamCount() {
		return _params.size();
	}
}
