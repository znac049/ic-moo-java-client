package uk.org.wookey.IC.newUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import uk.org.wookey.IC.Utils.Logger;

public class JSEngine {
	private final static String scriptDir = "macros/";
	private Logger _logger = new Logger("JSEngine");
	
	private ScriptEngine js;
	
	public JSEngine() {
		ScriptEngineManager factory = new ScriptEngineManager();
	
		js = factory.getEngineByName("JavaScript");
	
		//js.put("gateway", new Gateway());
	
		try {
			js.eval(new FileReader("scripts/fred.js"));
		} catch (FileNotFoundException | ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runFile(String name) {
		File f = new File(scriptDir + name);
		
		if (!f.exists()) {
			_logger.logError("Couldn't find javascript file to run");
			_logger.logError("Script path was: " + f.getAbsolutePath());
		}
		
		// File exists
		try {
			js.eval(new FileReader(f.getAbsolutePath()));
		} catch (FileNotFoundException | ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
