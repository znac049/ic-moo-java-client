package uk.org.wookey.IC.Editor;

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import org.fife.ui.rtextarea.RTextScrollPane;

import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.Logger;

public class EditorForm extends JFrame implements ActionListener {
	private static final long serialVersionUID = 7555977762161202604L;
	private final Logger _logger = new Logger("EditorForm");
	protected String _type;
	protected NewGenericEditor _editor;
	protected JMenuBar _menu;
	protected String saveName;
	protected String editorName;
	
	public EditorForm(String name, String type, String content) {
		super();
		
		editorName = "Edit";
		
		_menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);	

		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.setMnemonic(KeyEvent.VK_S);
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		saveItem.addActionListener(this);
		fileMenu.add(saveItem);

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
		exitItem.addActionListener(this);
		fileMenu.add(exitItem);
		_menu.add(fileMenu);

		setJMenuBar(_menu);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		_type = type;
		
		_editor = new NewGenericEditor();
		_editor.setText(content);
		_editor.setName(name + ":" + type);
		//setSaveName(name);
		
		setTitle(getEditorName() + ": " + name);

		RTextScrollPane scroller = new RTextScrollPane(_editor);
		add(scroller, BorderLayout.CENTER);

		setLocation(300, 300);
		setSize(700, 500);

		setVisible(true);
	}
	
	public void setSaveName(String newName) {
		saveName = newName;
	}
	
	public String getEditorName() {
		return editorName;
	}
	
	public String getText() {
		return _editor.getText();
	}

	public void setSyntax(String syntax) {
		_editor.setSyntaxEditingStyle(syntax);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equalsIgnoreCase("Exit")) {
			setVisible(false);
			dispose();
		}
		else if (cmd.equalsIgnoreCase("Save")) {
			_logger.logMsg("Save back to MOO - NOT!");
			// worldTab.writeRemote(":blinks");
			setVisible(false);
			dispose();
		}
	}
	
	public boolean saveLocalCopy() {
		_logger.logInfo("Save local copy to '" + saveName + "'");
		
		try {
			_editor.write(new FileWriter("code/" + saveName));
		} catch (IOException e) {
			_logger.logError("Failed to save local copy of document");
			return false;
		}
		
		_logger.logSuccess("Saved local copy to 'code/" + saveName + "'");
		
		return true;
	}
}
