package uk.org.wookey.ICPlugin.LocalEditing;

import uk.org.wookey.IC.Utils.CorePluginInterface;
import uk.org.wookey.IC.Utils.IOPluginInterface;
import uk.org.wookey.IC.Utils.Line;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ParserException;
import uk.org.wookey.IC.Utils.IOPlugin;
import uk.org.wookey.IC.Utils.StringParser;

public class LocalEditing extends IOPlugin {
	public final Logger _logger = new Logger("LocalEditing");
	private String outOfBandToken = "#$#";
	private StringParser cmdParser = new StringParser("");
	
	public boolean activate() {
		setName("LocalEditing");
		
		return true;
	}
	
	@Override
	public Status remoteLineIn(Line l) {
		String cmd;
		String name = "";
		String upload = "";
		String bit;
		String line = l.get();
		
		if (!line.startsWith(outOfBandToken)) {
			return IOPluginInterface.Status.IGNORED;
		}
		
		line = line.substring(outOfBandToken.length());
		cmdParser.setString(line.trim());
		
		//_logger.logMsg("Handle '" + line + "'");

		try {
			cmd = cmdParser.nextItem();
			//_logger.logMsg("Command is '" + cmd + "'");
			
			if (cmd.equalsIgnoreCase("edit")) {
				String type = "moo-code";
				String content = gobbleRemote();
				
				name = cmdParser.nextItem();
				if (!name.equalsIgnoreCase("name:")) {
					_logger.logMsg("Badly formatted OOB edit command");
					return IOPluginInterface.Status.CONSUMED;
				}
				name = "";
				bit = cmdParser.nextItem();
				while (!bit.equalsIgnoreCase("upload:")) {
					name += bit + " ";
					bit = cmdParser.nextItem();
				}
				
				name = name.trim();
				upload = cmdParser.getRemainingLine().trim();
				
				//_logger.logMsg("Name='" + name + "'");
				//_logger.logMsg("Upload='" + upload + "'");
				//_logger.logMsg("Type='" + type + "'");
				//_logger.logMsg("Content='" + content + "'");
				
				new LocalEditorForm(name, type, upload, content, server);

				return IOPluginInterface.Status.CONSUMED;
			}
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return IOPluginInterface.Status.IGNORED;
	}
	
	private String gobbleRemote() {
		String gobbled = "";
		String line;
		
		line = server.readLine();
		//_logger.logMsg("Gobble: '" + line + "'");
			
		while (line.compareTo(".") != 0) {
			gobbled += line + '\n';
			line = server.readLine();
			//_logger.logMsg("Gobble: '" + line + "'");
		}
		
		return gobbled;
	}
	
	public boolean supports(CorePluginInterface.PluginType pluginType) {
		switch (pluginType) {
		case IOPLUGIN:
			return true;
		}
		
		return true;
	}
}