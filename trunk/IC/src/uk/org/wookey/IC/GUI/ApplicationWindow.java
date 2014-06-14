package uk.org.wookey.IC.GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.TabInterface;

public class ApplicationWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private final static Logger _logger = new Logger("ApplicationWindow");
	private static JTabbedPane tabs;
	
	public ApplicationWindow() {
		super("IC");
		
		GridBagConstraints gbc = new GridBagConstraints();
		MainMenuBar menu;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		
		gbc.insets = new Insets(2, 2, 2, 2);
		
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.anchor = GridBagConstraints.PAGE_START;
		
		setSize(900, 700);
		setLocation(100, 0);

		setLayout(new GridBagLayout());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menu = new MainMenuBar();
		menu.add(new QuickLaunch());
		setJMenuBar(menu);
		
		//add(new QuickLaunch(), gbc);
		
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
		
		gbc.gridy++;
		gbc.weighty = 1.0;
		add(tabs, gbc);
		
		gbc.gridy++;
		gbc.weighty = 0.0;
		
		//add(MainStatusBar.getBar(), gbc);
		
		setVisible(true);
	}

	public static void addTab(WorldTab tab) {
		String title;
		
		//Tray.activate();
		
		title = tab.getWorldName();
		tabs.addTab(title, tab.getIndicator(), tab);
		tabs.setSelectedComponent(tab);
		
		int index = tabs.indexOfTab(title);
		JPanel newTab = new JPanel(new GridBagLayout());
		newTab.setOpaque(false);
		JLabel titleLab = new JLabel(title + "  ", tab.getIndicator(), JLabel.LEFT);
		
		BufferedImage buttonIcon = null;
		BufferedImage nullIcon = null;
		try {
			nullIcon = ImageIO.read(new File("images/blank.png"));
			buttonIcon = ImageIO.read(new File("images/cross.png"));
		} catch (IOException e) {
			_logger.logError("Failed to load cross.png");
		}
		JButton closeButton = new JButton(new ImageIcon(nullIcon));
		closeButton.setRolloverIcon(new ImageIcon(buttonIcon));
		closeButton.setBorder(BorderFactory.createEmptyBorder());
		closeButton.setContentAreaFilled(false);
		closeButton.setRolloverEnabled(true);
		closeButton.addMouseListener(tab);
					
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		
		newTab.add(titleLab, gbc);

		gbc.gridx++;
		gbc.weightx = 0;
		newTab.add(closeButton, gbc);

		tabs.setTabComponentAt(index, newTab);
	}
}
