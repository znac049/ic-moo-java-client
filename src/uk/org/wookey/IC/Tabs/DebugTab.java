package uk.org.wookey.IC.Tabs;

import java.awt.*;

import javax.swing.*;

import uk.org.wookey.IC.Interfaces.TabInterface;
import uk.org.wookey.IC.Utils.Logger;

public class DebugTab extends GenericTab implements TabInterface {
	private static final long serialVersionUID = 6325314968517017778L;
	private static JTextPane _log;

	public DebugTab() {
		super();
		
		setLayout(new BorderLayout());
		
		_log = new JTextPane();
		_log.setEditable(false);
		_log.setBackground(new Color(0xe0, 0xff, 0xf0));
		_log.setFont(new Font("Courier", Font.PLAIN, 12));

		JScrollPane scroller = new JScrollPane(_log);
		add(scroller, BorderLayout.CENTER);
		new Logger(_log);
	}
}