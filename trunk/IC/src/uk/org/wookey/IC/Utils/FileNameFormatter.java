package uk.org.wookey.IC.Utils;

import java.util.HashMap;
import java.util.Map;

public class FileNameFormatter {
	private Map<String, String> mappings;
	
	public FileNameFormatter() {
		mappings = new HashMap<String, String>();
	}
	
	public String makeSpecialFilename(String format) {
		String res = "";
		
		for (int i = 0; i < format.length(); i++){
		    char c = format.charAt(i);
		    
		    if (c == '%') {
		    	i++;
		    	c = format.charAt(i);
		    	
		    	if (mappings.containsKey(String.valueOf(c))) {
		    		res += mappings.get(String.valueOf(c));
		    	}
		    	else {
		    		res += "%" + c;
		    	}
		    }
		    else {
		    	res += c;
		    }
		}
		
		return res;
	}
	
	public void addMapping(String key, String val) {
		mappings.put(key,  val);
	}
}
