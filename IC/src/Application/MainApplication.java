package Application;

import java.io.IOException;
import java.util.prefs.Preferences;

import uk.org.wookey.IC.GUI.ApplicationWindow;
import uk.org.wookey.IC.GUI.WorldTab;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.PluginManager;
import uk.org.wookey.IC.Utils.Prefs;
import uk.org.wookey.IC.Utils.TimedEvent;
import uk.org.wookey.IC.Utils.TimerProcess;
import uk.org.wookey.IC.Utils.Worlds;

public class MainApplication {
	private static ApplicationWindow mainWindow;
	private final Logger _logger = new Logger("MainApplication");

	public static ApplicationWindow getAppWindow() {
		return mainWindow;
	}
	
	public MainApplication() {
		mainWindow = new ApplicationWindow();
		
		// Plugins
		PluginManager.scanForPlugins();
		
		// Timers
		(new Thread(TimerProcess.getTimerProcess())).start();
		
		TimedEvent ev = new TimedEvent();
		ev.setRepeat(5000, 5);
		TimerProcess.queueTimerEvent(ev);
		
		ev = new TimedEvent();
		ev.setRepeat(3000);
		TimerProcess.queueTimerEvent(ev);

		ev = new TimedEvent();
		ev.setRepeat(17000);		
		TimerProcess.queueTimerEvent(ev);
		
		// Check for worlds to autoconnect to
		for (String world: Worlds.getListOfWorlds()) {
			Preferences prefs = Preferences.userRoot().node(Prefs.WorldsRoot + "/" + world);
			
			if (prefs.getBoolean(Prefs.AUTOCONNECT, false)) {
				_logger.logMsg("Autoconnect to world '" + world + "'");
				
				// Create a tab and connect
				String worldServer = prefs.get(Prefs.SERVER, null);
				int worldPort = prefs.getInt(Prefs.PORT, -1);
				if ((worldServer != null) && (worldPort != -1)) {
					try {
						WorldTab tab = new WorldTab(world);
						mainWindow.addTab(tab);
						tab.runThread();
					} catch (IOException e) {
						_logger.logMsg("Caught exception trying to create WorldTab('" + world + "'");
					}
				}
			}
				
		}
		
		mainWindow.revalidate();
		mainWindow.repaint();
	}

	public static void main(String[] args) {
		new MainApplication();
	}
}
