package uk.org.wookey.IC.OOB;

import java.util.ArrayList;

import uk.org.wookey.IC.Interfaces.OOBHandlerInterface;
import uk.org.wookey.IC.MCP.MCPException;
import uk.org.wookey.IC.MCP.MCPRoot;
import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.Logger;

public class OOBHandlers implements OOBHandlerInterface {
	private Logger _logger = new Logger("OOBHandlers");
	private ArrayList<OOBHandlerInterface> _handlers;
	private WorldTab _worldTab;
	
	public OOBHandlers(WorldTab worldTab) {
		MCPRoot mcp;
		OOBLocalEdit localEdit;
	
		_logger.logMsg("Creating OOBHandlers");
		
		_worldTab = worldTab;
		
		_handlers = new ArrayList<OOBHandlerInterface>();
		
		// Only load the handlers needed by this world
		
		try {
			localEdit = new OOBLocalEdit(worldTab);
		} catch (Exception e) {
			localEdit = null;
		}
		
		if (localEdit != null) {
			_handlers.add(localEdit);
		}

		try {
			mcp = new MCPRoot(worldTab);
		} catch (MCPException e) {
			mcp = null;
		}
		
		if (mcp != null) {
			_handlers.add(mcp);
		}
	}
	
	@Override
	public boolean isOutOfBand(String line) {
		for (OOBHandlerInterface o : _handlers) {
			if (o.isOutOfBand(line)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int handle(String line) {
		int code = OOBNotInterested;
		
		for (OOBHandlerInterface o : _handlers) {
			_logger.logMsg("Passing to " + o.getHandlerName() + " to handle");
			code = o.handle(line);
			
			if (code == OOBHandledFinal) {
				// Handled and don't want anyone else to be allowed a look-in
				return code;
			}
		}
		
		return code;
	}

	@Override
	public String getHandlerName() {
		return "OOBHandlers";
	}
}
