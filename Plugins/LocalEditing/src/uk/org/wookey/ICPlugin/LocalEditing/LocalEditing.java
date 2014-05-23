package uk.org.wookey.ICPlugin.LocalEditing;

import java.io.IOException;

import javax.swing.JPanel;

import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.Plugin;
import uk.org.wookey.IC.Utils.StringParser;

public class LocalEditing extends Plugin {
	public final Logger _logger = new Logger("LocalEditing");
	private String outOfBandToken = "#$#";
	private StringParser cmdParser = new StringParser("");

	@Override
	public boolean energizePlugin() {
		setName("Local Editing");
		_logger.logMsg("Hello from the local editing plugin");
		
		return true;
	}
	
	@Override
	public boolean handlesRemoteLineInput() {
		return true;
	}
	
	@Override
	public int handleRemoteLineInput(String line) {
		String cmd;
		String name = "";
		String upload = "";
		String bit;
		
		if (!line.startsWith(outOfBandToken)) {
			return NotInterested;
		}
		
		line = line.substring(outOfBandToken.length());
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
					return HandledFinal;
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
				
				new LocalEditorForm(name, type, upload, content, _worldTab);

				return HandledFinal;
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return NotInterested;
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