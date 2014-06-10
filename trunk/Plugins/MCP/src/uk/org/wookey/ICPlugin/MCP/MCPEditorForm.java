package uk.org.wookey.ICPlugin.MCP;

import java.awt.event.*;

import javax.swing.*;

import uk.org.wookey.IC.Editor.EditorForm;
import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ServerPort;

public class MCPEditorForm extends EditorForm implements ActionListener {
	private static final long serialVersionUID = 8974376117801769307L;
	private final Logger _logger = new Logger("MCPEditorForm");
	private String _key;
	private String _ref;
	private ServerPort _server;
	public MCPEditorForm(String name, String ref, String type, String content, ServerPort svr, String key) {
		super(name, type, content);
		
		_ref = ref;
		_key = key;
		_server = svr;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equalsIgnoreCase("Exit")) {
			setVisible(false);
			dispose();
		}
		else if (cmd.equalsIgnoreCase("Save")) {
			MCPCommand command = new MCPCommand();
			command.setName("dns-org-mud-moo-simpleedit-set");
			command.setKey(_key);
			command.addParam("reference", _ref);
			command.addParam("type", _type);
			command.addParam("content*", _editor.getText());
			
			_logger.logMsg("Save back to MOO (MCP)!");
			// worldTab.writeRemote(":blinks");
			command.sendToServer(_server);
			setVisible(false);
			dispose();
		}
	}
}
