package uk.org.wookey.IC.Utils;

import uk.org.wookey.IC.newUtils.CorePluginInterface;

public class CorePlugin implements CorePluginInterface {
	private String name;
	
	public CorePlugin() {
		name = null;
	}
	
	@Override
	public boolean supports(PluginType t) {
		return false;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void setName(String n) {
		name = n;
	}

}
