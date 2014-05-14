package uk.org.wookey.IC.OOB;

import java.io.IOException;

import uk.org.wookey.IC.Editor.OOBEditorForm;
import uk.org.wookey.IC.Interfaces.OOBHandlerInterface;
import uk.org.wookey.IC.MCP.MCPException;
import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.StringParser;

public class OOBLocalEdit implements OOBHandlerInterface {
	private Logger _logger = new Logger("OOB LocalEdit");
	private WorldTab _worldTab;
	private String outOfBandToken = "#$#";
	private StringParser cmdParser = new StringParser("");
	
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
		String name = "";
		String upload = "";
		String bit;
		
		cmdParser.setString(line.trim());
		
		_logger.logMsg("Handle '" + line + "'");

		try {
			cmd = cmdParser.nextItem();
			_logger.logMsg("Command is '" + cmd + "'");
			
			if (cmd.equalsIgnoreCase("edit")) {
				String type = "moo-code";
				String content = gobbleRemote();
				
				name = cmdParser.nextItem();
				if (!name.equalsIgnoreCase("name:")) {
					_logger.logMsg("Badly formatted OOB edit command");
					return OOBHandledFinal;
				}
				name = "";
				bit = cmdParser.nextItem();
				while (!bit.equalsIgnoreCase("upload:")) {
					name += bit + " ";
					bit = cmdParser.nextItem();
				}
				
				name = name.trim();
				upload = cmdParser.getRemainingLine().trim();
				
				_logger.logMsg("Name='" + name + "'");
				_logger.logMsg("Upload='" + upload + "'");
				_logger.logMsg("Type='" + type + "'");
				_logger.logMsg("Content='" + content + "'");
				
				new OOBEditorForm(name, type, upload, content, _worldTab);

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
