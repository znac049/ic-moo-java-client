package uk.org.wookey.ICPlugin.MCP;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.StringParser;

public class MCPStringParser extends StringParser {
	Logger _logger = new Logger("MCPStringParser");
	
	public MCPStringParser(String line) {
		super(line.trim());
	}

	public String nextItem() throws ParserException {
		String item = "";
		
		//_logger.logInfo("nextItem: Line: '" + _line + "'");
		
		if (_line.length() == 0) {
			return _line;
		}

		if (_line.charAt(0) == '"') {
			//_logger.logInfo("quoted");
			item = nextQuotedItem();
		}
		else {
			//_logger.logInfo("unquoted");
			item = nextUnquotedItem();
		}
		
		//_logger.logSuccess("nextItem: item='" + item + "', remainder='" + _line + "'");
		return item;
	}
	
	private String nextQuotedItem() throws ParserException {
		int len = _line.length();
		
		if (len < 2) {
			throw new ParserException("Quoted string not long enough for opening and closing quotes");
		}
		
		int endQuote = _line.indexOf('"', 1);
		String item = _line.substring(1, endQuote);
		
		if ((endQuote+1) == len) {
			// No more _line left..
			_line = "";
		}
		else {
			_line = _line.substring(endQuote+2).trim();
		}
		
		return item;
	}
	
	private String nextUnquotedItem() {
		int nextSpace = _line.indexOf(' ');
		String item = "";
		
		if (nextSpace < 0) {
			item = _line;
			_line = "";
		}
		else {
			item = _line.substring(0, nextSpace);
			_line = _line.substring(nextSpace+1).trim();
		}
		return item;
	}
}
