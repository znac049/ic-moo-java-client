package uk.org.wookey.IC.Utils;

import java.util.ArrayList;

public class CommandHistory {
	private ArrayList<String> commands = new ArrayList<String>();
	private int maxHistory = 100;
	private int currentIndex;
	
	public CommandHistory() {
		commands.clear();
		currentIndex = commands.size() - 1;
	}
	
	public void add(String command) {
		commands.add(command);
		
		while (commands.size() > maxHistory) {
			commands.remove(0);
		}
		
		currentIndex = commands.size() - 1;
	}
	
	public void previousCommand() {
		if (currentIndex >= 0) {
			currentIndex--;
		}
	}
	
	public void nextCommand() {
		if (currentIndex < (commands.size() - 1)) {
			currentIndex++;
		}
	}
	
	public String fetchCommand() {
		if (currentIndex < 0) {
			return null;
		}
		
		return commands.get(currentIndex);
	}
}
