package Application;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;

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
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		    	//_logger.logInfo("L&F: " + info.getName());
		    	
		        if ("Metal".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		mainWindow = new ApplicationWindow();
		pickScreenAndResize(mainWindow);
		
		AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory)TokenMakerFactory.getDefaultInstance();
		atmf.putMapping("text/moo", "uk.org.wookey.IC.MOOLanguage.MOOTokenMaker");
		
		// Plugins
		PluginManager.scanForPlugins();
		
		// Timers
		(new Thread(TimerProcess.getTimerProcess())).start();
		
		TimedEvent ev = new TimedEvent();
		ev.setRepeat(5000, 5);
		//TimerProcess.queueTimerEvent(ev);
		
		ev = new TimedEvent();
		ev.setRepeat(3000);
		//TimerProcess.queueTimerEvent(ev);

		ev = new TimedEvent();
		ev.setRepeat(17000);		
		//TimerProcess.queueTimerEvent(ev);
		
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
	
	private void pickScreenAndResize(ApplicationWindow win) {
		// Display on the largest (area) screen in a multi-screen setup
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = g.getScreenDevices();
		int bestScreen = -1;
		int bestArea = -1;
		int appWidth = win.getWidth();
		int appHeight = win.getHeight();
		
		_logger.logInfo("Number of screens found: " + devices.length);
		for (int i = 0; i < devices.length; i++) {
			int width = devices[i].getDisplayMode().getWidth();
			int height = devices[i].getDisplayMode().getHeight();
			int area = width*height;
			
			if (area > bestArea) {
				bestArea = area;
				bestScreen = i;
			}
			
			_logger.logInfo("  #" + i + ": " + width + " x " + height);
		}
		
		// Resize it a bit if we can
		if (bestScreen != -1) {
			GraphicsDevice best = devices[bestScreen];
			Rectangle rect = best.getDefaultConfiguration().getBounds();
			int screenWidth = best.getDisplayMode().getWidth();
			int screenHeight = best.getDisplayMode().getHeight();
			
			if (screenWidth >= 1360) {
				appWidth = 1360;
				
				if (screenWidth >= 1920) {
					appWidth = 1700;
				}
			}
			
			if (screenHeight >= 768) {
				appHeight = 768;
				
				if (screenHeight >= 1080) {
					appHeight = 900;
				}
			}
			
			win.setSize(appWidth, appHeight);

			int xOff = (screenWidth - appWidth) / 2;
			int yOff = (screenHeight - appHeight) / 2;
				
			_logger.logInfo("Rect: " + rect.x + "x" + rect.y);
			_logger.logInfo("Screen: " + screenWidth + "x" + screenHeight);
			_logger.logInfo("App: " + appWidth + "x" + appHeight);
			_logger.logInfo("Offsets: " + xOff + "x" + yOff);
				
			win.setLocation(rect.x + xOff, rect.y + yOff);
		}
	}

	public static void main(String[] args) {
		new MainApplication();
	}
}
