package uk.org.wookey.IC.GUI;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FormItem extends JPanel {
	private static final long serialVersionUID = 1L;
	String label;
	Component comp;
	
	public FormItem(String label, JComponent comp) {
		this.label = label;
		this.comp = comp;
		
		setAlignmentX(LEFT_ALIGNMENT);
		
		JLabel l = new JLabel(this.label);
		l.setAlignmentX(LEFT_ALIGNMENT);
		
		comp.setAlignmentX(LEFT_ALIGNMENT);
		add(l);
		add(this.comp);
	}
}
