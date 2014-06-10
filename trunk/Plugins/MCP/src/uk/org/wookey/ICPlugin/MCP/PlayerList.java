package uk.org.wookey.ICPlugin.MCP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import uk.org.wookey.IC.Utils.TimeUtils;

public class PlayerList extends JPanel {
    private static final long serialVersionUID = 1590212426402405754L;

	static class MyCellRenderer extends JPanel implements ListCellRenderer {
		private static final long serialVersionUID = 6774711558579077438L;
		JLabel player;
		JLabel idle;
		
		public MyCellRenderer() {
			setLayout(new GridLayout(1, 2));
			player = new JLabel();
			idle = new JLabel();
			
			player.setOpaque(true);
			idle.setOpaque(true);
			
			add(player);
			add(idle);
		}
		
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Player p = (Player) value;
			player.setText(p.getName() + "(" + p.getId() + ")  ");
			idle.setText(TimeUtils.approxString(p.getIdle()));
			
			long idleTime = Long.parseLong(p.getIdle());
			
			Color bg;
			Color fg;
			Color listFg;
			
			if (isSelected) {
				bg = list.getSelectionBackground();
				listFg = list.getSelectionForeground();
				
	        	//player.setBackground(list.getSelectionBackground());
	        	//player.setForeground(list.getSelectionForeground());

	        	//idle.setBackground(list.getSelectionBackground());
	        	//idle.setForeground(list.getSelectionForeground());
	        } 
			else {
				bg = list.getBackground();
				listFg = list.getForeground();
				
				//player.setBackground(list.getBackground());
				//player.setForeground(list.getForeground());
				
				//idle.setBackground(list.getBackground());
				//idle.setForeground(list.getForeground());
			}
			
			if (idleTime <= 300) {
				fg = mergeColors(listFg, new Color(0, 128, 0));
			}
			else if (idleTime <= 900) {
				fg = mergeColors(listFg, new Color(192, 64, 0));
			}
			else {
				fg = mergeColors(listFg, new Color(96, 0, 64));
			}
			
			player.setBackground(bg);
			player.setForeground(fg);
			
			idle.setBackground(bg);
			idle.setForeground(fg);
			
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			
			return this;
		}
		
		private Color mergeColors(Color a, Color b) {
			int red = a.getRed() + b.getRed();
			int green = a.getGreen() + b.getGreen();
			int blue = a.getBlue() + b.getBlue();
			
			if (red > 255) {
				red = 255;
			}
			
			if (green > 255) {
				green = 255;
			}
			
			if (blue > 255) {
				blue = 255;
			}
			
			return new Color(red, green, blue);
		}
    }

	private ArrayList<Player> players;
	private JList connectedPlayers;

	public PlayerList() {
		super();
		
		setLayout(new BorderLayout());
		setBorder(new LineBorder(Color.black));
		
		JLabel title = new JLabel("Connected Players", JLabel.CENTER);
		add(title, BorderLayout.NORTH);
		
		players = new ArrayList<Player>();
		connectedPlayers = new JList(players.toArray());
		connectedPlayers.setBackground(getBackground());
		connectedPlayers.setCellRenderer(new MyCellRenderer());
		add(connectedPlayers, BorderLayout.CENTER);
	}
	
	public void add(Player p) {
		players.add(p);
		Sorter.sort(players);
		connectedPlayers.setListData(players.toArray());
	}
	
	public void setData(ArrayList<Player> p) {
		players = p;
		Sorter.sort(players);
		connectedPlayers.setListData(players.toArray());
	}
}
