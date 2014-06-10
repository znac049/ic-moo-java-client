package uk.org.wookey.ICPlugin.MCP;

import java.io.IOException;

import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.CorePluginInterface;
import uk.org.wookey.IC.Utils.IOPluginInterface;
import uk.org.wookey.IC.Utils.Line;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.IOPlugin;
import uk.org.wookey.IC.Utils.StringParser;
import uk.org.wookey.IC.Utils.IOPluginInterface.Status;

public class MCP extends IOPlugin {
	public final Logger _logger = new Logger("MCP");
	private String outOfBandToken = "#$#";
	private StringParser cmdParser = new StringParser("");
	private MCPRoot mcp;

	public boolean activate() {
		setName("MCP");
		
		try {
			mcp = new MCPRoot(server);
		} catch (ParserException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public boolean supports(CorePluginInterface.PluginType pluginType) {
		switch (pluginType) {
		case IOPLUGIN:
			return true;
		}
		
		return true;
	}

	public Status remoteLineIn(Line l) {
		String line = l.get();
		
		if (!line.startsWith(outOfBandToken)) {
			return IOPluginInterface.Status.IGNORED;
		}
		
		line = line.substring(outOfBandToken.length());
		l.set(line);
		
		return mcp.remoteLineIn(l);
	}	
}
