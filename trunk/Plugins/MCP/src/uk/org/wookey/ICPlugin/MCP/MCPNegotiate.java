package uk.org.wookey.ICPlugin.MCP;

import java.util.ArrayList;

import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.ParserException;

public class MCPNegotiate extends MCPHandler {
	private boolean _negotiating = true;
	private ArrayList<MCPPackage> _canList;
	
	public MCPNegotiate(WorldTab tab, MCPRoot mcpRoot) throws ParserException {
		super("mcp-negotiate", "1.0", "2.0", tab, mcpRoot);
		
		_canList = new ArrayList<MCPPackage>();
		_canList.clear();
		
		worldTab = tab;
	}
	
	public void handle(MCPCommand command, String key) {
		if (command.getName().equalsIgnoreCase("mcp-negotiate-can")) {
			handleCan(command);
		}
		else if (command.getName().equalsIgnoreCase("mcp-negotiate-end")) {
			handleEnd(command);
		}
	}

	private void handleEnd(MCPCommand command) {
		_negotiating = false;
	}

	private void handleCan(MCPCommand command) {
		if (_negotiating) {
			String packageName = command.getParam("package");
			String minVersion = command.getParam("min-version");
			String maxVersion = command.getParam("max-version");

			_canList.add(new MCPPackage(packageName, minVersion, maxVersion));
			
			// Do we support this package? If we do, then call then activate it at our end.
			mcp.activate(packageName, minVersion, maxVersion);
		}
	}
}