package uk.org.wookey.IC.GUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.org.wookey.IC.Interfaces.TabInterface;

public class WorldTabs extends JFrame {	
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabPane;
	
	public WorldTabs() {
		super("IC");

		setLayout(new BorderLayout());
		
		tabPane = new JTabbedPane();
		tabPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
	            TabInterface panel = (TabInterface) tabPane.getSelectedComponent();
               	panel.clearActivity();
            }
        });
	
		getContentPane().add(tabPane, BorderLayout.CENTER);
		getContentPane().add(new MainStatusBar(), BorderLayout.SOUTH);
	}
	
	public JTabbedPane getTabPane() {
		return tabPane;
	}
}
