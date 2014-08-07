package uk.org.wookey.IC.GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import uk.org.wookey.IC.Utils.CorePlugin;
import uk.org.wookey.IC.Utils.CorePluginInterface.PluginType;
import uk.org.wookey.IC.Utils.IOPlugin;
import uk.org.wookey.IC.Utils.PluginManager;
import webBoltOns.layoutManager.GridFlowLayout;
import webBoltOns.layoutManager.GridFlowLayoutParameter;

public class SettingsForm extends JDialog {
	private static final long serialVersionUID = 5212833746627586111L;

	public SettingsForm() {
		super(new JFrame(), "Global Settings", true);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		ArrayList<CorePlugin> plugins = PluginManager.pluginsSupporting(PluginType.IOPLUGIN);
		for (CorePlugin p: plugins) {
			GlobalConfigPanel conf = ((IOPlugin) p).getGlobalSettings();
			
			add(conf);
		}
		
		JPanel buttons = new JPanel();
		
		JButton okBtn = new JButton("OK");
		buttons.add(okBtn);
		
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		});
		
		JButton cancelBtn = new JButton("Cancel");
		buttons.add(cancelBtn);
		
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		});
		
		add(buttons);
		
		setLocation(300, 150);
		setSize(500, 350);
		pack();
		setResizable(false);
		setVisible(true);
	}	
}
