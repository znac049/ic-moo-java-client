package uk.org.wookey.IC.Utils;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.*;

public class Logger {
	private static JTextPane _log = null;
	private String _logName;
	private int _logLevel = 1;
	private SimpleAttributeSet _msgAttribs;
	private SimpleAttributeSet _labAttribs;
	private SimpleAttributeSet _errAttribs;
	private ArrayList<String[]> _msgBuffer;
	
	public Logger(JTextPane log)
	{
		_log = log;
		initialise("");
	}
	
	public Logger(String tag) {
		initialise(tag);
	}
	
	public Logger(String tag, int level) {
		initialise(tag);
		_logLevel = level;
	}
	
	private void initialise(String tag) {
		if (tag.equals("")) {
			_logName = "";
		}
		else {
			_logName = tag + ":";
		}
		
		_msgBuffer = new ArrayList<String[]>();
		
		_msgAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(_msgAttribs, Color.white);
		StyleConstants.setBold(_msgAttribs, false);

		_labAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(_labAttribs, Color.orange);

		_errAttribs = new SimpleAttributeSet();
		StyleConstants.setForeground(_errAttribs, Color.red);
	}
	
	public void logMsg(String msg) {
		if (_logLevel == 0) {
			return;
		}
		
		if (_log != null) {
			if (_msgBuffer.size() > 0) {
				for (int i=0; i<_msgBuffer.size(); i++) {
					String bits[] = _msgBuffer.get(i);
					append(bits[0], _labAttribs);
					append(bits[1], _msgAttribs);
				}
				_msgBuffer.clear();
			}
			append(_logName, _labAttribs);
			append(' ' + msg + '\n', _msgAttribs);
		}
		else {
			// We don't have a text pane yet - buffer it for later display.
			String item[] = new String[2];
			item[0] = _logName;
			item[1] = msg;
			_msgBuffer.add(item);
			
			System.out.println(_logName + ' ' +msg);
		}
	}
	
	public void printBacktrace(Exception e) {
		StackTraceElement[] trace = e.getStackTrace();

		logMsg("Caught an exception at:");
		for (int i=0; i<trace.length; i++) {
			append(trace[i].toString() + '\n', _errAttribs);
		}
	}
	
	protected void append(String msg, SimpleAttributeSet attributes) {
		Document doc = _log.getDocument();
		
		try {
			doc.insertString(doc.getLength(), msg, attributes);
			_log.setCaretPosition(doc.getLength());		
		} catch (BadLocationException e) {
			e.printStackTrace();
		}		
	}
}
