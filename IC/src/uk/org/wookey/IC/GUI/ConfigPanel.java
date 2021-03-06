package uk.org.wookey.IC.GUI;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import uk.org.wookey.IC.Utils.ConfigInterface;
import uk.org.wookey.IC.Utils.Logger;

public class ConfigPanel extends JPanel {
	private static Logger _logger = new Logger("ConfigPanel");
	private static final long serialVersionUID = 1L;
	
	private ConfigInterface configClass = null;

	public ConfigPanel(String name) {
		super();
		
		//setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setLayout(new GridLayout(0, 1));
		
		setBorder(BorderFactory.createTitledBorder(name));
	}
	
	public void addItem(String label, JComponent container) {
		FormItem item = new FormItem(label, container);
		
		add(item);
	}
	
	public void addItem(JComponent container) {
		addItem("", container);
	}
	
	public void registerConfigHandler(ConfigInterface configClass) {
		this.configClass = configClass;
	}
	
	public boolean saveChanges() {
		_logger.logInfo("Saving changes");
		
		if (configClass != null) {
			configClass.saveConfig(ConfigInterface.CONFIG_GLOBAL);
		}
		return true;
	}
}
