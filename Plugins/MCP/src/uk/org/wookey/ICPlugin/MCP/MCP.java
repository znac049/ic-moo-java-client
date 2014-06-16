package uk.org.wookey.ICPlugin.MCP;

import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.CorePluginInterface;
import uk.org.wookey.IC.Utils.IOPluginInterface;
import uk.org.wookey.IC.Utils.Line;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.IOPlugin;
import uk.org.wookey.IC.Utils.ServerPort;

public class MCP extends IOPlugin {
	public final Logger _logger = new Logger("MCP");
	private String outOfBandToken = "#$#";
	private MCPRoot mcp = null;

	public boolean activate() {		
		setName("MCP");
		
		return true;
	}
	
	public boolean attach(ServerPort svr, WorldTab tab) {		
		try {
			mcp = new MCPRoot(svr, tab);
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
