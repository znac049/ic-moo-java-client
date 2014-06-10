package uk.org.wookey.IC.Utils;

public interface CorePluginInterface {
	public final static int interfaceVersion = 1;
	
	public enum PluginType {
		IOPLUGIN,
		MACROPLUGIN,
	}
	
	public boolean activate();
	public boolean supports(PluginType t);
	public String getName();
	public void setName(String n);
}
