package uk.org.wookey.IC.newUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.script.ScriptEngine;

import uk.org.wookey.IC.Editor.EditorForm;
import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.IC.newGUI.MacroEditorForm;

public class Macro {
	private enum MacroType {
		JAVASCRIPT
	}
	
	private Logger _logger = new Logger("Macro");
	
	private String name;
	private String jsFile;
	private MacroType type;
	
	public Macro(String macroName) {
		type = MacroType.JAVASCRIPT;
		
		if (macroName.endsWith(".js")) {
			macroName = macroName.substring(0, macroName.length() - 3);
		}
		
		name = macroName;
		jsFile = macroName + ".js";
	}
	public Macro(String macroName, String js) {
		type = MacroType.JAVASCRIPT;
		
		name = macroName;
		jsFile = js;
	}
	
	public boolean exec(JSEngine js, ServerPort server) {
		boolean res = false;
		
		_logger.logInfo("Execute macro " + name);
		
		File f = new File(MacroManager.macrosDir + name + ".js");
		if (f.exists() & f.canRead()) {
			js.put("server", server);
			
			js.exec(f);
			
			res = true;
		}
		
		return res;
	}
	
	public void edit() {
		File f = new File(MacroManager.macrosDir + name + ".js");
		String contents = "";
		if (f.exists() & f.canRead()) {
			contents = readFile(f);
		}
		
		new MacroEditorForm("Macro: " + name, "Javascript", contents, f);
	}
	
	String readFile(File f) {
		return readFile(f.getAbsoluteFile().toString(), Charset.defaultCharset());
	}
	
	String readFile(String path, Charset encoding) {
		try {
			byte[] encoded;

			encoded = Files.readAllBytes(Paths.get(path));
			
			return new String(encoded, encoding);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String getName() {
		return name;
	}
}
