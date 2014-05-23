package uk.org.wookey.ICPlugin.MCP;

import java.io.IOException;

import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.Plugin;
import uk.org.wookey.IC.Utils.StringParser;

public class MCP extends Plugin {
	public final Logger _logger = new Logger("MCP");
	private String outOfBandToken = "#$#";
	private StringParser cmdParser = new StringParser("");
	private MCPRoot mcp;

	@Override
	public boolean energizePlugin() {
		setName("MCP");
		_logger.logMsg("Hello from the MCP plugin");
		
		return true;
	}

	@Override
	public boolean onConnect() {
		try {
			mcp = new MCPRoot(_worldTab);
		} catch (ParserException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	
	@Override
	public boolean handlesRemoteLineInput() {
		return true;
	}
	
	@Override
	public int handleRemoteLineInput(String line) {
		if (!line.startsWith(outOfBandToken)) {
			return NotInterested;
		}
		
		line = line.substring(outOfBandToken.length());

		return mcp.handleRemoteLineInput(line);
	}	
}
