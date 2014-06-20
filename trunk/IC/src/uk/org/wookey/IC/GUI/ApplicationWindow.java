package uk.org.wookey.IC.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.TabInterface;

public class ApplicationWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final static Logger _logger = new Logger("ApplicationWindow");
	private JTabbedPane tabs;
	private MainStatusBar statusBar;
	
	public ApplicationWindow() {
		super("IC");

		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		
		gbc.insets = new Insets(2, 2, 2, 2);
		
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.anchor = GridBagConstraints.PAGE_START;
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = tk.getScreenSize().width;
		int ySize = tk.getScreenSize().height;
		
		if (xSize > 1360) {
			xSize = 1360;
		}
		
		if (ySize > 768) {
			ySize = 768;
		}
		
		setSize(xSize, ySize);
		setLocation((tk.getScreenSize().width - xSize) / 2, (tk.getScreenSize().height - ySize) / 2);

		setLayout(new GridBagLayout());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MainMenuBar menu = new MainMenuBar();
		menu.add(new QuickLaunch(this));
		setJMenuBar(menu);
		
		tabs = new JTabbedPane();
		
		// Turn the activity LED off when a tab gets focus
        tabs.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		TabInterface tab = (TabInterface) tabs.getSelectedComponent();
        		tab.clearActivity();
        	}
        });
        menu.addTabs(tabs);

		tabs.add("Console", new DebugTab());
		
		gbc.gridy = 1;
		gbc.weighty = 1.0;
		add(tabs, gbc);
		
		statusBar = new MainStatusBar(); 
		JPanel outer = new JPanel();
		outer.setLayout(new BorderLayout());
		outer.add(statusBar, BorderLayout.EAST);
		outer.setBackground(new Color(0xd0, 0xd0, 0xd0));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		//gbc.gridwidth = 3;
		add(outer, gbc);
		
		setVisible(true);
	}

	public void addTab(WorldTab tab) {
		String title;
		
		title = tab.getName();
		tabs.addTab(title, tab.getIndicator(), tab);
		tabs.setSelectedComponent(tab);
		
		int index = tabs.indexOfTab(title);
		tabs.setTabComponentAt(index, new TabLabel(title, tab.getIndicator(), this, tab));
	}
	
	class FocusHandler implements FocusListener {
		@Override
		public void focusGained(FocusEvent fev) {
			Component c = fev.getComponent();
			c.setBackground(c.getBackground().darker());
		}

		@Override
		public void focusLost(FocusEvent fev) {
			Component c = fev.getComponent();
			c.setBackground(c.getBackground().brighter());
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		_logger.logInfo("Got a close tab click");
		Component x = (Component) event.getSource();
		
		x = x.getParent();
		
		if (x instanceof TabLabel) {
			_logger.logInfo("It's a WorldTab");
			WorldTab tab = ((TabLabel)x).getWorldTab();
			
			tab.tearDown();
			
			for (int i=0; i<tabs.getComponentCount(); i++) {
				if (tabs.getComponentAt(i) == tab) {
					_logger.logInfo("Found world tab at index " + i);
					tabs.remove(i);
					break;
				}
			}
		}
		else {
			_logger.logInfo("Not sure what to do with this type of tab: " + x.toString());
		}
	}
}
