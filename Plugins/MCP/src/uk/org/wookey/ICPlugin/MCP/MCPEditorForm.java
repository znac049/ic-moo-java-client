package uk.org.wookey.ICPlugin.MCP;

import java.awt.Event;
import java.awt.event.*;

import javax.swing.*;

import uk.org.wookey.IC.Editor.EditorForm;
import uk.org.wookey.IC.Editor.NewGenericEditor;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ServerConnection;

public class MCPEditorForm extends EditorForm implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final Logger _logger = new Logger("MCPEditorForm");
	private String _key;
	private String _ref;
	
	private MCP _mcp;
	//private ServerConnection _server;
	
	public MCPEditorForm(String name, String ref, String type, String content, MCP mcp, ServerConnection svr, String key) {
		super(name, type, content);
		
		_ref = ref;
		_key = key;
		
		_mcp = mcp;
		//_server = svr;
		
		setSyntax(NewGenericEditor.SYNTAX_STYLE_MOO);
		
		JMenuItem uploadItem = new JMenuItem("Upload");
		uploadItem.setMnemonic(KeyEvent.VK_U);
		uploadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, Event.CTRL_MASK));
		uploadItem.addActionListener(this);
		
		for (int i = 0; i < _menu.getMenuCount(); i++) {
			JMenu menu = _menu.getMenu(i);
			
			_logger.logInfo("Check Menu '" + menu.getText() + "'");
			
			if (menu.getText().equalsIgnoreCase("File")) {
				menu.add(uploadItem);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equalsIgnoreCase("Exit")) {
			setVisible(false);
			dispose();
		}
		else if (cmd.equalsIgnoreCase("Upload")) {
			saveLocalCopy();

			MCPCommand command = new MCPCommand();
			command.setName("dns-org-mud-moo-simpleedit-set");
			command.setAuthKey(_key);
			command.addParam("reference", _ref);
			command.addParam("type", _type);
			command.addParam("content*", getText());
			
			_mcp.queueOutgoingCommand(command);
			//command.sendToServer(_server);
		}
		else if (cmd.equalsIgnoreCase("Save")) {
			saveLocalCopy();

			MCPCommand command = new MCPCommand();
			command.setName("dns-org-mud-moo-simpleedit-set");
			command.setAuthKey(_key);
			command.addParam("reference", _ref);
			command.addParam("type", _type);
			command.addParam("content*", getText());
			
			_mcp.queueOutgoingCommand(command);
			//command.sendToServer(_server);
			
			setVisible(false);
			dispose();
		}
	}
}
