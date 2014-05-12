package uk.org.wookey.IC.Editor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import uk.org.wookey.IC.MCP.*;
import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.Logger;

public class EditorForm extends JFrame implements ActionListener {
	private static final long serialVersionUID = 7555977762161202604L;
	private final Logger _logger = new Logger("EditorForm");
	GenericEditor _editor;
	WorldTab worldTab;
	String _key;
	String _ref;
	String _type;
	
	public EditorForm(String name, String ref, String type, String content, WorldTab tab, String key) {
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
		fileMenu.add(exitItem);
		menu.add(fileMenu);

		setJMenuBar(menu);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		_ref = ref;
		_type = type;
		worldTab = tab;
		_key = key;
		if (type.equals("moo-code")) {
			_editor = new MOOCodeEditor();
		}
		else {
			_editor = new GenericEditor();
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
			MCPCommand command = new MCPCommand();
			command.setName("dns-org-mud-moo-simpleedit-set");
			command.setKey(_key);
			command.addParam("reference", _ref);
			command.addParam("type", _type);
			command.addParam("content*", _editor.getText());
			
			_logger.logMsg("Save back to MOO!");
			// worldTab.writeRemote(":blinks");
			command.sendToServer(worldTab);
			setVisible(false);
			dispose();
		}
	}
}
