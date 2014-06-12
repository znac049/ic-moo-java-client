package uk.org.wookey.ICPlugin.LocalEditing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import uk.org.wookey.IC.Editor.EditorForm;
import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.ServerPort;

public class LocalEditorForm extends EditorForm implements ActionListener {
	private static final long serialVersionUID = 4644731315830687023L;
	private final Logger _logger = new Logger("OOBEditorForm");
	private String _upload;
	private ServerPort server;

	public LocalEditorForm(String name, String type, String upload, String content, ServerPort svr) {
		super(name, type, content);
		
		_upload = upload;
		server = svr;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equalsIgnoreCase("Exit")) {
			setVisible(false);
			dispose();
		}
		else if (cmd.equalsIgnoreCase("Save")) {
			_logger.logMsg("Save back to MOO (OOB)!");
			saveLocalCopy();
			server.writeLine(_upload);
			server.writeLine(_editor.getText());
			server.writeLine(".");
			//server.writeLine(":blinks");
			setVisible(false);
			dispose();
		}
	}

}
