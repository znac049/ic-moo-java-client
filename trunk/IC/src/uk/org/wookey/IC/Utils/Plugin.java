package uk.org.wookey.IC.Utils;

import java.util.prefs.Preferences;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Application.MainApplication;
import uk.org.wookey.IC.Tabs.WorldTab;
import uk.org.wookey.IC.newUtils.Prefs;

public class Plugin {
	private Logger _logger = new Logger("Base Plugin");
	private String _name;
	protected WorldTab _worldTab = null;
	protected String _worldName = null;
	protected boolean _enabled = false; // Disabled by default
	
	private Preferences appRoot = Prefs.node(Prefs.AppRoot);
	private Preferences globalPluginSettings = null;
	private Preferences localPluginSettings = null;
	
	public static final int Handled = 1;
	public static final int HandledFinal = 2;
	public static final int NotInterested = 3;
	
	protected JPanel settingsPanel = null;
	protected JCheckBox pluginEnabled;
	
	public Plugin() {
		setName("*noname*");
	}
	
	public String getName() {
		return _name;
	}
	
	protected void setName(String name) {
		_name = name;
		globalPluginSettings = appRoot.node(Prefs.pluginNodeName + "/" + _name);

		loadSettings();
	}
	
	public boolean energizePlugin() {
		setName("Base Plugin");
		
		return true;
	}
	
	public boolean connectTo(WorldTab worldTab) {
		_worldTab = worldTab;
		return connectTo(worldTab.getWorldName());
	}

	public boolean connectTo(String worldName) {
		_worldName = worldName;
		
		if (energizePlugin()) {
			return onConnect();
		}
		
		return false;
	}

	public boolean onConnect() {
		localPluginSettings = Prefs.node(Prefs.WorldsRoot + "/" + _worldName);
		
		loadSettings();
		
		return true;
	}

	public boolean handlesRemoteLineInput() {
		return false;
	}
	
	public boolean handlesRemoteLineOutput() {
		return false;
	}
	
	public int handleRemoteLineInput(String line) {
		return NotInterested;
	}
	
	public boolean enabled() {
		return _enabled;
	}
	
	public String getStringSetting(String settingName) {
		String res = "";
		
		if (globalPluginSettings != null) {
			res = globalPluginSettings.get(settingName, res);
			_logger.logMsg("getStringSetting('" + globalPluginSettings.absolutePath() + "/" + settingName + "' = " + res);
		}
		
		if (localPluginSettings != null) {
			res = localPluginSettings.get(settingName, res);
			_logger.logMsg("getStringSetting('" + localPluginSettings.absolutePath() + "/" + settingName + "' = " + res);
		}
		
		return res;
	}
	
	public int getIntSetting(String settingName) {
		int res = 0;
		
		if (globalPluginSettings != null) {
			res = globalPluginSettings.getInt(settingName, res);
			_logger.logMsg("getIntSetting('" + globalPluginSettings.absolutePath() + "/" + settingName + "' = " + res);
		}
		
		if (localPluginSettings != null) {
			res = localPluginSettings.getInt(settingName, res);
			_logger.logMsg("getIntSetting('" + localPluginSettings.absolutePath() + "/" + settingName + "' = " + res);
		}
		
		return res;
	}
	
	public boolean getBooleanSetting(String settingName) {
		boolean res = false;
		
		if (globalPluginSettings != null) {
			res = globalPluginSettings.getBoolean(settingName, res);
			_logger.logMsg("getBooleanSetting('" + globalPluginSettings.absolutePath() + "/" + settingName + "' = " + res);
		}
		
		if (localPluginSettings != null) {
			res = localPluginSettings.getBoolean(settingName, res);
			_logger.logMsg("getBooleanSetting('" + localPluginSettings.absolutePath() + "/" + settingName + "' = " + res);
		}
		
		return res;
	}
	
	public void putSetting(String settingName, String val) {
		if (localPluginSettings != null) {
			localPluginSettings.put(settingName, val);
			_logger.logMsg("putSetting('" + localPluginSettings.absolutePath() + "/" + settingName + "' -> '" + val + "'");
		}
		else if (globalPluginSettings != null) {
			globalPluginSettings.put(settingName, val);
			_logger.logMsg("putSetting('" + globalPluginSettings.absolutePath() + "/" + settingName + "' -> '" + val + "'");
		}
	}
	
	public void putIntSetting(String settingName, int val) {
		if (localPluginSettings != null) {
			localPluginSettings.putInt(settingName, val);
			_logger.logMsg("putIntSetting('" + localPluginSettings.absolutePath() + "/" + settingName + "' -> " + val);
		}
		else if (globalPluginSettings != null) {
			globalPluginSettings.putInt(settingName, val);
			_logger.logMsg("putIntSetting('" + globalPluginSettings.absolutePath() + "/" + settingName + "' -> " + val);
		}
	}

	public void putBooleanSetting(String settingName, boolean val) {
		if (localPluginSettings != null) {
			localPluginSettings.putBoolean(settingName, val);
			_logger.logMsg("putBooleanSetting('" + localPluginSettings.absolutePath() + "/" + settingName + "' -> " + val);
		}
		else if (globalPluginSettings != null) {
			globalPluginSettings.putBoolean(settingName, val);
			_logger.logMsg("putBooleanSetting('" + globalPluginSettings.absolutePath() + "/" + settingName + "' -> " + val);
		}
	}

	public JPanel getWorldSettingsTab() {
		loadSettings();
		
		if (settingsPanel == null) {
			settingsPanel = new JPanel();
		
			settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
		
			JLabel title = new JLabel(_name + " Settings");
			settingsPanel.add(title);

			pluginEnabled = new JCheckBox("Plugin enabled");
			pluginEnabled.setSelected(_enabled);
			settingsPanel.add(pluginEnabled);
		}
		
		return settingsPanel;
	}
	
	public void loadSettings() {
		_enabled = getBooleanSetting("Enabled");
	}
	
	public void saveSettings() {
		if (settingsPanel == null) {
			_logger.logMsg("saveSettings(); called on plugin with settingsPanel null!");
		}
		else {
			_logger.logMsg("Plugin enabled??? " + pluginEnabled.isSelected());
			
			_enabled = pluginEnabled.isSelected();
			putBooleanSetting("Enabled", _enabled);
		}
	}

}
