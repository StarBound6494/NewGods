package com.fluff_stuff.newgods;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Prefix {
	static String marrigePrefix=ChatColor.RED+('\u2764'+" "+ChatColor.WHITE);
	static String leaderPrefix=('\u2720'+" ");
	static String priestPrefix=ChatColor.GRAY+('\u2719'+" "+ChatColor.WHITE);
	
	static ArrayList<String> heartPlayers = new ArrayList<String>();
	static ArrayList<String> leaderPlayers = new ArrayList<String>();
	static ArrayList<String> priestPlayers = new ArrayList<String>();
	
	public static void RemovePlayer(String player){
		if(NewGods.allowPrefixs==false){return;}
		heartPlayers.remove(player);
		leaderPlayers.remove(player);
		priestPlayers.remove(player);
	}
	
	public static void ChatUpdate(Player p){
		if(NewGods.allowPrefixs==false){return;}
		String name=p.getName();
		if(heartPlayers.contains(name)){AddHeart(p);}
		if(leaderPlayers.contains(name)){AddLeader(p);}
		if(priestPlayers.contains(name)){AddPriest(p);}
	}
	
	public static void AddHeart(Player p){
		if(NewGods.allowPrefixs==false){return;}
		String n = p.getDisplayName();
		if(n.contains(marrigePrefix)==false){
			p.setDisplayName(marrigePrefix+n);
			if(heartPlayers.contains(p.getName())==false){
				heartPlayers.add(p.getName());
			}
		}
	}
	
	public static void AddLeader(Player p){
		if(NewGods.allowPrefixs==false){return;}
		String n = p.getDisplayName();
		if(n.contains(leaderPrefix)==false){
			int playerID = NewGods.data.getPlayerID(p.getName());
			String god = NewGods.data.playerGod.get(playerID);
			int godID = NewGods.data.getGodID(god);	
			if (godID!=-1) {				
				p.setDisplayName(ChatColor.valueOf(NewGods.data.godType.get(godID))+leaderPrefix+ChatColor.valueOf("WHITE")+n);
				if(leaderPlayers.contains(p.getName())==false){
					leaderPlayers.add(p.getName());
				}
			}
		}
	}
	
	public static void AddPriest(Player p){
		if(NewGods.allowPrefixs==false){return;}
		String n = p.getDisplayName();
		if(n.contains(priestPrefix)==false){
			p.setDisplayName(priestPrefix+n);
			if(priestPlayers.contains(p.getName())==false){
				priestPlayers.add(p.getName());
			}
		}
	}
	
	public static void RemoveHeart(Player p) {
		if(NewGods.allowPrefixs==false){return;}
		String d = p.getDisplayName();
		p.setDisplayName(d.replaceAll(marrigePrefix, ""));
		if(heartPlayers.contains(p.getName())){
			heartPlayers.remove(p.getName());
		}
	}
	
	public static void RemoveLeader(Player p) {
		if(NewGods.allowPrefixs==false){return;}
		String d = p.getDisplayName();
		p.setDisplayName(d.replaceAll(leaderPrefix, ""));
		if(leaderPlayers.contains(p.getName())){
			leaderPlayers.remove(p.getName());
		}
	}
	
	public static void RemovePriest(Player p) {
		if(NewGods.allowPrefixs==false){return;}
		String d = p.getDisplayName();
		p.setDisplayName(d.replaceAll(priestPrefix, ""));
		if(priestPlayers.contains(p.getName())){
			priestPlayers.remove(p.getName());
		}
	}
}
