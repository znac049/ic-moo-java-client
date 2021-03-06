package uk.org.wookey.IC.Utils;

public class StringParser {
	private final Logger _logger = new Logger("StringParser");
	protected String _line;
	
	public StringParser(String line) {
		_line = line;
	}
	
	public void setString(String line) {
		_line = line;
	}
	
	public String nextItem() throws ParserException {
		int space = _line.indexOf(' ');
		int quote = _line.indexOf('"');
		String item = "";
		
		//_logger.logInfo("Line: '" + _line + "'");
		
		if ((space == -1) && (quote == -1)) {
			item = _line;
			_line = "";
		}
		else if (((space != -1) && (quote != -1) && (space < quote)) 
				|| (quote == -1)) {
			// space first
			item = _line.substring(0, space);
			_line = _line.substring(space+1);
		}
		else {
			// quote first
			int endquote = _line.indexOf('"', quote+1);
			
			if (endquote == -1) {
				throw new ParserException("Unbalanced quotes.");
			}
			item = _line.substring(quote+1, endquote);
			if (_line.charAt(endquote+1) == ' ') {
				_line = _line.substring(endquote+2);
			}
			else {
				_line = _line.substring(endquote+1);
			}
		}
		
		//_logger.logMsg("Item='" + item + "', line='" + _line + "'");
		return item;
	}

	public String getRemainingLine() {
		String line = _line;
		
		_line = "";
		return line;
	}
}
