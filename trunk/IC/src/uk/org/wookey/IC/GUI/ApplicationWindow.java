package uk.org.wookey.IC.GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import uk.org.wookey.IC.Factories.PluginFactory;
import uk.org.wookey.IC.Factories.WorldTabFactory;
import uk.org.wookey.IC.Tabs.DebugTab;
import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.Logger;

public class ApplicationWindow {
	private final Logger _logger = new Logger("ApplicationWindow");
	private JFrame appWindow;
	private JTabbedPane tabs;
	
	public ApplicationWindow(boolean b) {
		appWindow = new JFrame("IC");
		
		appWindow.setSize(800, 600);
		appWindow.setLocation(100, 100);

		appWindow.setLayout(new BorderLayout());
		
		appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appWindow.setJMenuBar(new MainMenuBar());
		
		appWindow.add(new QuickLaunch(), BorderLayout.NORTH);
		
		tabs = new JTabbedPane();
		tabs.add("Console", new DebugTab());
		appWindow.add(tabs, BorderLayout.AFTER_LAST_LINE);
		
		//appWindow.pack();
		
		appWindow.setVisible(true);
	}

	public void addTab(WorldTab tab) {
		tabs.addTab(tab.getWorldName(), tab.getIndicator(), tab);
		tabs.setSelectedComponent(tab);
	}
}
