package uk.org.wookey.IC.GUI;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.org.wookey.IC.Interfaces.TabInterface;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.newGUI.DebugTab;
import uk.org.wookey.IC.newGUI.WorldTab;

public class ApplicationWindow {
	@SuppressWarnings("unused")
	private final Logger _logger = new Logger("ApplicationWindow");
	private static JFrame appWindow = null;
	private static JTabbedPane tabs;
	
	public ApplicationWindow() {
		if (appWindow != null) {
			return;
		}
		
		appWindow = new JFrame("IC");
		
		appWindow.setSize(800, 600);
		appWindow.setLocation(100, 100);

		appWindow.setLayout(new BoxLayout(appWindow.getContentPane(), BoxLayout.Y_AXIS));
		
		appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appWindow.setJMenuBar(new MainMenuBar());
		
		appWindow.add(new QuickLaunch());
		
		tabs = new JTabbedPane();
		// Turn the activity LED off when a tab gets focus
        tabs.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		TabInterface tab = (TabInterface) tabs.getSelectedComponent();
        		tab.clearActivity();
        	}
        });

		tabs.add("Console", new DebugTab());
		appWindow.add(tabs);
		
		appWindow.add(new MainStatusBar());
		
		appWindow.setVisible(true);
	}

	public static void addTab(WorldTab tab) {
		
		if (appWindow == null) {
			new ApplicationWindow();
		}
		
		tabs.addTab(tab.getWorldName(), tab.getIndicator(), tab);
		tabs.setSelectedComponent(tab);
	}
}
