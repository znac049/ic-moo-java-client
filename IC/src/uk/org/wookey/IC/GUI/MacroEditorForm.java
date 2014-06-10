package uk.org.wookey.IC.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import uk.org.wookey.IC.Editor.EditorForm;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.newUtils.ServerPort;

public class MacroEditorForm  extends EditorForm implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final Logger _logger = new Logger("MacroEditorForm");
	File file;

	public MacroEditorForm(String name, String type, String content, File f) {
		super(name, type, content);
		file = f;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equalsIgnoreCase("Exit")) {
			setVisible(false);
			dispose();
		}
		else if (cmd.equalsIgnoreCase("Save")) {
			_logger.logMsg("Save macro");
			saveFile();
			setVisible(false);
			dispose();
		}
	}

	private void saveFile() {
		try {
			PrintWriter out = new PrintWriter(file.getAbsolutePath());
			
			out.print(_editor.getText());
			out.close();
		} catch (FileNotFoundException e) {
			_logger.logError("Couldn't write macro to disk");
			e.printStackTrace();
		}
	}
}
