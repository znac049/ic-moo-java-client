package uk.org.wookey.IC.GUI;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import webBoltOns.layoutManager.GridFlowLayout;
import webBoltOns.layoutManager.GridFlowLayoutParameter;

public class SettingsForm extends JFrame {
	private static final long serialVersionUID = 5212833746627586111L;
	private JTextField pluginDir;

	public SettingsForm() {
		super("Settings");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setLayout(new GridFlowLayout(4, 6));
		
		pluginDir = new JTextField(20);
		addComp("Plugin Directory", pluginDir);
		
		setLocation(300, 300);
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	private void addComp(String label, Component comp) {
		JLabel lab = new JLabel(label, JLabel.TRAILING);
		add(lab, new GridFlowLayoutParameter(GridFlowLayoutParameter.NEXT_ROW, 1));

		lab.setLabelFor(comp);
		add(comp, new GridFlowLayoutParameter(GridFlowLayoutParameter.CURRENT_ROW, 2));		
	}


}
