package uk.org.wookey.ICPlugin.MCP;

import java.awt.Event;
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
			command.addParam("content*", _editor.getText());
			
			command.sendToServer(_server);
		}
		else if (cmd.equalsIgnoreCase("Save")) {
			saveLocalCopy();

			MCPCommand command = new MCPCommand();
			command.setName("dns-org-mud-moo-simpleedit-set");
			command.setAuthKey(_key);
			command.addParam("reference", _ref);
			command.addParam("type", _type);
			command.addParam("content*", _editor.getText());
			
			command.sendToServer(_server);
			setVisible(false);
			dispose();
		}
	}
}
