package uk.org.wookey.IC.GUI.Forms;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.org.wookey.IC.GUI.GlobalConfigPanel;
import uk.org.wookey.IC.Utils.CorePlugin;
import uk.org.wookey.IC.Utils.CorePluginInterface.PluginType;
import uk.org.wookey.IC.Utils.IOPlugin;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.Utils.PluginManager;

public class SettingsForm extends JDialog {
	private static Logger _logger = new Logger("Settings Form");
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ConfigPanel> panels; 

	public SettingsForm() {
		super(new JFrame(), "Global Settings", true);
		
		panels = new ArrayList<ConfigPanel>();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		ArrayList<CorePlugin> plugins = PluginManager.pluginsSupporting(PluginType.IOPLUGIN);
		for (CorePlugin p: plugins) {
			GlobalConfigPanel conf = ((IOPlugin) p).getGlobalSettings();
			
			addPanel(conf);
		}
		
		JPanel buttons = new JPanel();
		
		JButton okBtn = new JButton("OK");
		buttons.add(okBtn);
		
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (saveSettings()) {
					setVisible(false);
				}
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
	
	private void addPanel(ConfigPanel conf) {
		panels.add(conf);
		add(conf);
	}
	
	private boolean saveSettings() {
		_logger.logInfo("Saving global settings...");
		
		for (ConfigPanel panel: panels) {
			panel.saveChanges();
		}
		
		return true;
	}
}
