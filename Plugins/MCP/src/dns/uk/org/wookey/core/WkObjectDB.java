package dns.uk.org.wookey.core;

import java.util.ArrayList;

import uk.org.wookey.IC.Utils.Logger;
import uk.org.wookey.ICPlugin.MCP.MCP;
import uk.org.wookey.ICPlugin.MCP.MCPException;

public class WkObjectDB {
	private static Logger _logger = new Logger("WkObjectDB");
	private ArrayList<WkObject> db;
	private int maxObjNum;
	
	private MCP mcp;
	
	public WkObjectDB(MCP m) {
		db = new ArrayList<WkObject>();
		maxObjNum = -1;
		
		mcp = m;
	}
	
	public WkObject getObject(int objNum) throws MCPException {
		if ((objNum < 0) || (objNum > maxObjNum)) {
			throw new MCPException("Object number out of range");
		}
		
		//_logger.logInfo("Look up object #" + objNum);
		for (WkObject o: db) {
			if (o.getObjNum() == objNum) {
				//_logger.logInfo("Found it");
				
				return o;
			}
		}
		
		//_logger.logInfo("No object - creating one");
		
		WkObject obj = new WkObject(objNum, mcp);
		db.add(obj);
		
		return obj;
	}
	
	public boolean objectExists(int objNum) {
		//_logger.logInfo("Does object #" + objNum + " exist?");
		if ((objNum < 0) || (objNum > maxObjNum)) {
			//_logger.logInfo("No - out of range");

			return false;
		}
		
		for (WkObject o: db) {
			if (o.getObjNum() == objNum) {
				//_logger.logInfo("Yes");
				
				return true;
			}
		}

		//_logger.logInfo("No");

		return false;
	}
	
	public void setMaxObject(int objNum) {
		if (objNum > 0) {
			maxObjNum = objNum;
		}
	}
	
	public static int decodeObjectNum(String objStr) throws MCPException {
		String oid = objStr;
		
		if (!(oid.substring(0, 1) == "#")) {
			oid = oid.substring(1);
		}
					
		try {
			int objNum = Integer.parseInt(oid);
			
			return objNum;
		}
		catch (NumberFormatException e) {
			_logger.logError("Badly formatted object number: " + objStr);
			throw new MCPException("Badly formatted object number: " + objStr);
		}
	}

	public static int decodeObjectNumNoEx(String objStr)  {
		String oid = objStr;
		
		//_logger.logInfo("Decoding string: '" + objStr + "', len=" + objStr.length());
		
		if (objStr.equals("")) {
			return -1;
		}
		
		if (!(oid.substring(0, 1) == "#")) {
			oid = oid.substring(1);
		}		
			
		try {
			int objNum = Integer.parseInt(oid);
			
			return objNum;
		}
		catch (NumberFormatException e) {
			_logger.logError("Badly formatted object number: " + objStr);
			return -1;
		}
	}
}
