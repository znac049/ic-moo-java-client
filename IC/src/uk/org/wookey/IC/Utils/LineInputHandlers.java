package uk.org.wookey.IC.Utils;

import java.util.ArrayList;

import uk.org.wookey.IC.Factories.PluginFactory;
import uk.org.wookey.IC.MCP.MCPException;
import uk.org.wookey.IC.MCP.MCPRoot;
import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.Logger;

public class LineInputHandlers {
	private Logger _logger = new Logger("LineInputHandlers");
	private ArrayList<Plugin> _handlers;
	private WorldTab _worldTab;
	
	public LineInputHandlers(WorldTab worldTab) {
		WorldCache cache = new WorldCache();
		WorldSettings detail = cache.getWorld(worldTab.getWorldName());

		MCPRoot mcp;
	
		_logger.logMsg("Creating OOBHandlers");
		
		_worldTab = worldTab;
		
		_handlers = new ArrayList<Plugin>();
		
		// Only load the handlers needed by this world
		try {
			mcp = new MCPRoot(worldTab);
		} catch (MCPException e) {
			mcp = null;
		}
		
		if (mcp != null) {
			_handlers.add(mcp);
		}
		
		ArrayList<Plugin> plugins = PluginFactory.getRemoteInputPlugins();
		for (Plugin plugin: plugins) {
			if (plugin.connectTo(worldTab)) {
				_handlers.add(plugin);
			}
			else {
				_logger.logMsg("Plugin '" + plugin.getName() + "' failed to connect to worldTab");
			}
		}
	}
	
	public int handleRemoteLineInput(String line) {
		int code = Plugin.NotInterested;
		
		for (Plugin plugin : _handlers) {
			code = plugin.handleRemoteLineInput(line);
			
			if (code == Plugin.HandledFinal) {
				// Handled and don't want anyone else to be allowed a look-in
				return code;
			}
		}
		
		return code;
	}
}
