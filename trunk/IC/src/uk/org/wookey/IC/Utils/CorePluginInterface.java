package uk.org.wookey.IC.Utils;

import uk.org.wookey.IC.GUI.ConfigPanel;
import uk.org.wookey.IC.GUI.GlobalConfigPanel;
import uk.org.wookey.IC.GUI.WorldTab;

public interface CorePluginInterface {
	public final static int interfaceVersion = 1;
	
	public enum PluginType {
		IOPLUGIN,
		MACROPLUGIN,
	}
	
	public boolean activate();
	public void deactivate();
	public boolean supports(PluginType t);
	public String getName();
	public void setName(String n);
	
	public String getVersionString();
	
	public GlobalConfigPanel getGlobalSettings();
	public ConfigPanel getWorldSettings(WorldTab worldTab);
}
