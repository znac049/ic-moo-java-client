package uk.org.wookey.IC.GUI;

import javax.swing.JFrame;
import uk.org.wookey.IC.Factories.WorldTabFactory;
import uk.org.wookey.IC.Tabs.DebugTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.WorldCache;
import uk.org.wookey.IC.Utils.WorldDetail;

public class ApplicationWindow {
	private final Logger _logger = new Logger("ApplicationWindow");
	WorldTabs _mainWindow;
	
	public ApplicationWindow() {
		_mainWindow = WorldTabFactory.getWorldTabs();
		_mainWindow.setSize(800, 600);
		_mainWindow.setLocation(100, 100);

		_mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		_mainWindow.getTabPane().addTab("Console", new DebugTab());
		//_mainWindow.getContentPane().add(new LED(255,0,0));
		MainMenuBar menuBar = new MainMenuBar();
		_mainWindow.setJMenuBar(menuBar);
				
		//frame.pack();
		_mainWindow.setVisible(true);
		
		// Read configuration details
		readPreferences();
	}
	
	private void readPreferences() {
		WorldCache cache = new WorldCache();
		
		// Attempt to connect to any worlds flagged for autoconnect
		String worldNames[];
		worldNames = cache.getWorldnames();
		_logger.logMsg("Found " + worldNames.length + " world entries");
		
		for (int i=0; i<worldNames.length; i++) {
			WorldDetail detail = cache.getWorld(worldNames[i]);
				
			if (detail.getAutoConnect()) {
				// Create a tab and connect
				String worldServer = detail.getServerName();
				int worldPort = detail.getServerPort();
				if ((worldServer != null) && (worldPort != -1)) {
					WorldTabFactory.getWorldTab(worldNames[i]);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new ApplicationWindow();
	}
}
