package uk.org.wookey.IC.GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import uk.org.wookey.IC.Factories.PluginFactory;
import uk.org.wookey.IC.Factories.WorldTabFactory;
import uk.org.wookey.IC.Tabs.DebugTab;
import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.WorldCache;
import uk.org.wookey.IC.Utils.WorldSettings;

public class ApplicationWindow {
	private final Logger _logger = new Logger("ApplicationWindow");
	private WorldTabs _OOOOOLDmainWindow;
	private JFrame appWindow;
	private JTabbedPane tabs;
	
	public ApplicationWindow() {		
		_OOOOOLDmainWindow = WorldTabFactory.getWorldTabs();
		_OOOOOLDmainWindow.setSize(800, 600);
		_OOOOOLDmainWindow.setLocation(100, 100);

		_OOOOOLDmainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		_OOOOOLDmainWindow.getTabPane().addTab("Console", new DebugTab());
		//_mainWindow.getContentPane().add(new LED(255,0,0));
		MainMenuBar menuBar = new MainMenuBar();
		_OOOOOLDmainWindow.setJMenuBar(menuBar);
				
		//frame.pack();
		_OOOOOLDmainWindow.setVisible(true);
		
		// Read configuration details
		readPreferences();
	}
	
	public ApplicationWindow(boolean b) {
		appWindow = new JFrame("IC");
		
		appWindow.setSize(800, 600);
		appWindow.setLocation(100, 100);
		
		appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appWindow.setJMenuBar(new MainMenuBar());
		
		appWindow.setLayout(new BorderLayout());
		appWindow.add(new QuickLaunch());
		
		tabs = new JTabbedPane();
		tabs.add("Console", new DebugTab());
		appWindow.add(tabs);
		
		appWindow.setVisible(true);
	}

	public void addTab(WorldTab tab) {
		tabs.addTab(tab.getWorldName(), tab.getIndicator(), tab);
		tabs.setSelectedComponent(tab);
	}
	
	private void readPreferences() {
		WorldCache cache = new WorldCache();
		
		// Attempt to connect to any worlds flagged for autoconnect
		String worldNames[];
		worldNames = cache.getWorldnames();
		_logger.logMsg("Found " + worldNames.length + " world entries");
		
		for (int i=0; i<worldNames.length; i++) {
			WorldSettings detail = cache.getWorld(worldNames[i]);
				
			_logger.logMsg("Locating settings for world '" + worldNames[i] + "'");
			
			if (detail.getBoolean(WorldDetailsPanel.AUTOCONNECT)) {
				_logger.logMsg("Autoconnect to world '" + worldNames[i] + "'");
				
				// Create a tab and connect
				String worldServer = detail.getString(WorldDetailsPanel.SERVER);
				int worldPort = detail.getInt(WorldDetailsPanel.PORT);
				if ((worldServer != null) && (worldPort != -1)) {
					WorldTabFactory.getWorldTab(worldNames[i]);
				}
			}
		}
	}
}
