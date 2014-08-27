package uk.org.wookey.IC.Utils;

import javax.swing.JComponent;


public interface ConfigInterface {
	public final static int CONFIG_GLOBAL = 1;
	public final static int CONFIG_WORLD = 2;
	
	public JComponent getConfigUI(int configType);
	public boolean saveConfig(int configType);
}
