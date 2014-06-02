package uk.org.wookey.IC.Utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MacroRunner {
	private ScriptEngine scriptEngine;

	public MacroRunner() {
		 ScriptEngineManager manager = new ScriptEngineManager ();
	     
		 scriptEngine = manager.getEngineByName ("JavaScript");
	}
}
