package uk.org.wookey.IC.newUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import uk.org.wookey.IC.Utils.Logger;

public class JSEngine {
	public final static String scriptDir = "macros/";
	private Logger _logger = new Logger("JSEngine");
	private Logger jsLogger = new Logger("JavaScript");
	
	private ScriptEngine js;
	
	public JSEngine() {
		ScriptEngineManager factory = new ScriptEngineManager();
	
		js = factory.getEngineByName("JavaScript");
	}
	
	public void exec(File f) {
		if (!f.exists()) {
			_logger.logError("Couldn't find javascript file to run");
			_logger.logError("Script path was: " + f.getAbsolutePath());
		}

		// Give it a default Logger
		js.put("_logger",  jsLogger);
		
		// Run the script
		try {
			js.eval(new FileReader(f.getAbsolutePath()));
		} catch (FileNotFoundException | ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void put(String name, Object o) {
		js.put(name, o);
	}
}
