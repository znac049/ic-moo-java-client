package uk.org.wookey.IC.GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.newGUI.DebugTab;
import uk.org.wookey.IC.newGUI.WorldTab;
import uk.org.wookey.IC.newUtils.TabInterface;
import uk.org.wookey.IC.newUtils.Tray;

public class ApplicationWindow {
	@SuppressWarnings("unused")
	private final static Logger _logger = new Logger("ApplicationWindow");
	private static JFrame appWindow = null;
	private static JTabbedPane tabs;
	
	public ApplicationWindow() {
		MainMenuBar menu;
		
		if (appWindow != null) {
			return;
		}
		
		appWindow = new JFrame("IC");
		
		appWindow.setSize(900, 700);
		appWindow.setLocation(100, 0);

		appWindow.setLayout(new BoxLayout(appWindow.getContentPane(), BoxLayout.Y_AXIS));
		
		appWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menu = new MainMenuBar();
		appWindow.setJMenuBar(menu);
		
		appWindow.add(new QuickLaunch());
		
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
		appWindow.add(tabs);
		
		//appWindow.add(new MainStatusBar());
		appWindow.add(MainStatusBar.getBar());
		
		appWindow.setVisible(true);
	}

	public static void addTab(WorldTab tab) {
		String title;
		
		if (appWindow == null) {
			new ApplicationWindow();
		}
		
		//Tray.activate();
		
		title = tab.getWorldName();
		tabs.addTab(title, tab.getIndicator(), tab);
		tabs.setSelectedComponent(tab);
		
		int index = tabs.indexOfTab(title);
		JPanel newTab = new JPanel(new GridBagLayout());
		newTab.setOpaque(false);
		JLabel titleLab = new JLabel(title + "  ", tab.getIndicator(), JLabel.LEFT);
		
		BufferedImage buttonIcon = null;
		try {
			buttonIcon = ImageIO.read(new File("images/cross.png"));
		} catch (IOException e) {
			_logger.logError("Failed to load cross.png");
		}
		JButton closeButton = new JButton(new ImageIcon(buttonIcon));
		closeButton.setBorder(BorderFactory.createEmptyBorder());
		closeButton.setContentAreaFilled(false);
					
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		
		newTab.add(titleLab, gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		newTab.add(closeButton, gbc);

		tabs.setTabComponentAt(index, newTab);

		//btnClose.addActionListener(myCloseActionHandler);
	}
}
