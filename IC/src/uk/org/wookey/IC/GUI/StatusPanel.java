package uk.org.wookey.IC.GUI;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JPanel;

import uk.org.wookey.IC.Utils.Logger;

public class StatusPanel extends JPanel {
	private Logger _logger = new Logger("StatusPanel");
	private ArrayList<Component> components;
	
	public StatusPanel() {
		super();
		
		this.setBackground(new Color(0x99, 0xff, 0x99));
		
		components = new ArrayList<Component>();
	}
	
	public void addMessage(String msg, int tag) {
		
	}
}
