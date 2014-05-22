package uk.org.wookey.ICPlugin.LocalEditing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import uk.org.wookey.IC.Editor.EditorForm;
import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.Logger;

public class LocalEditorForm extends EditorForm implements ActionListener {
	private static final long serialVersionUID = 4644731315830687023L;
	private final Logger _logger = new Logger("OOBEditorForm");
	private String _upload;

	public LocalEditorForm(String name, String type, String upload, String content, WorldTab tab) {
		super(name, type, content, tab);
		
		_upload = upload;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equalsIgnoreCase("Exit")) {
			setVisible(false);
			dispose();
		}
		else if (cmd.equalsIgnoreCase("Save")) {
			_logger.logMsg("Save back to MOO (OOB)!");
			worldTab.writeRemote(_upload);
			worldTab.writeRemote(_editor.getText());
			worldTab.writeRemote(".");
			worldTab.writeRemote(":blinks");
			setVisible(false);
			dispose();
		}
	}

}
