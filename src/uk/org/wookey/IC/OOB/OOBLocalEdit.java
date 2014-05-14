package uk.org.wookey.IC.OOB;

import java.io.IOException;

import uk.org.wookey.IC.Editor.EditorForm;
import uk.org.wookey.IC.Interfaces.OOBHandlerInterface;
import uk.org.wookey.IC.MCP.MCPException;
import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.StringParser;

public class OOBLocalEdit implements OOBHandlerInterface {
	private Logger _logger = new Logger("OOB LocalEdit");
	private WorldTab _worldTab;
	private String outOfBandToken = "#$#";
	private StringParser commandParser = new StringParser("");
	
	public OOBLocalEdit(WorldTab worldTab) {
		_worldTab = worldTab;
	}

	@Override
	public boolean isOutOfBand(String line) {
		return line.startsWith(outOfBandToken);
	}

	@Override
	public int handle(String line) {
		String cmd;
		
		commandParser.setString(line.trim());
		
		_logger.logMsg("Handle '" + line + "'");

		try {
			cmd = commandParser.nextItem();
			_logger.logMsg("Command is '" + cmd + "'");
			
			if (cmd.equalsIgnoreCase("edit")) {
				String ref = "moo-code-ref";
				String itemName = "some verb or something";
				String type = "moo-code";
				String content = gobbleRemote();
				String key = "xxx";
				
				_logger.logMsg("Ref='" + ref + "'");
				_logger.logMsg("Name='" + itemName + "'");
				_logger.logMsg("Type='" + type + "'");
				_logger.logMsg("Content='" + content + "'");
				
				new EditorForm(itemName, ref, type, content, _worldTab, key);

				return OOBHandledFinal;
			}
		} catch (MCPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return OOBNotInterested;
	}

	@Override
	public String getHandlerName() {
		return "OOBLocalEdit";
	}
	
	private String gobbleRemote() {
		String gobbled = "";
		String line;
		
		try {
			line = _worldTab.readRemote();
			_logger.logMsg("Gobble: '" + line + "'");
			
			while (line.compareTo(".") != 0) {
				gobbled += line + '\n';
				line = _worldTab.readRemote();
				_logger.logMsg("Gobble: '" + line + "'");
			}
		} catch (IOException e) {
			e.printStackTrace();
			
			return gobbled;
		}
		
		return gobbled;
	}

}
