package uk.org.wookey.IC.Editor;

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.Logger;

public class EditorForm extends JFrame implements ActionListener {
	private static final long serialVersionUID = 7555977762161202604L;
	private final Logger _logger = new Logger("EditorForm");
	protected String _type;
	protected GenericEditor _editor;
	protected String saveName;
	
	public EditorForm(String name, String type, String content) {
		super();
		
		JMenuBar menu = new JMenuBar();
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
		menu.add(fileMenu);

		setJMenuBar(menu);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		_type = type;
		if (type.equalsIgnoreCase("moo-code")) {
			_editor = new MOOCodeEditor();
			saveName = name + ".moo";
		}
		else if (type.equalsIgnoreCase("Javascript") | type.equalsIgnoreCase("JS")) {
			_editor = new JavascriptEditor();
			saveName = name + ".js";
		}
		else {
			_editor = new GenericEditor();
			saveName = name + ".txt";
		}
		
		_editor.colourize(content);
		setTitle(_editor.editorName() + ": " + name);

		JScrollPane scroller = new JScrollPane(_editor);
		add(scroller, BorderLayout.CENTER);

		setLocation(300, 300);
		setSize(500, 400);

		setVisible(true);
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
		_logger.logInfo("Save local copy");
		
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
