package uk.org.wookey.IC.newUtils;

import java.io.File;
import java.util.ArrayList;

import uk.org.wookey.IC.Utils.Logger;

public class MacroManager {
	private static final Logger _logger = new Logger("MacroManager");
	public static final String macrosDir = "./macros/";
	private static ArrayList<Macro> macros = null;
	
	public static void scanForMacros() {
		if (macros == null) {
			macros = new ArrayList<Macro>();
			File dir = new File(macrosDir);
			
			_logger.logMsg("Scanning for macros");
			if (dir.exists()) {
				File files[] = dir.listFiles();
			
				for (File file : files) {
					if (file.isFile()) {
						loadMacro(file);
					}
				}
			}
			else {
				_logger.logMsg("Plugin directory doesn't exist. Plugin scan aborted.");
			}
		}
		else {
			_logger.logMsg("Plugin scan only needs to happen once");
		}
	}

	private static void loadMacro(File file) {
		if (file.getName().endsWith(".js")) {
			String name;
			String fileName = file.getName();
			
			// We want this one
			name = fileName.substring(0, fileName.length()-3);
			_logger.logInfo("create macro '" + name + "'for file " + fileName);
			
			macros.add(new Macro(name, fileName));
		}
	}
	
	public static ArrayList<Macro> getMacroList() {
		if (macros == null) {
			scanForMacros();
		}
		
		return macros;
	}
	
}
