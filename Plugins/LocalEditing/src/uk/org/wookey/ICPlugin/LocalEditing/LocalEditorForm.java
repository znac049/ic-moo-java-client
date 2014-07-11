package uk.org.wookey.ICPlugin.LocalEditing;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import uk.org.wookey.IC.Editor.EditorForm;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ServerConnection;

public class LocalEditorForm extends EditorForm implements ActionListener {
	private static final long serialVersionUID = 4644731315830687023L;
	private final Logger _logger = new Logger("OOBEditorForm");
	private String _upload;
	private ServerConnection server;

	public LocalEditorForm(String name, String type, String upload, String content, ServerConnection svr) {
		super(name, type, content);
		
		_upload = upload;
		server = svr;
		
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
			server.writeLine(_upload);
			server.writeLine(getText());
			server.writeLine(".");
		}
		else if (cmd.equalsIgnoreCase("Save")) {
			saveLocalCopy();
			server.writeLine(_upload);
			server.writeLine(getText());
			server.writeLine(".");

			setVisible(false);
			dispose();
		}
	}

}
