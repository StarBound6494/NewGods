package com.fluff_stuff.newgods;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AcceptInfo {
	public String accepter;
	public String requester;
	public String situation;

	public AcceptInfo(String accepter, String requester, String situation) {
		this.accepter = accepter;
		this.requester = requester;
		this.situation = situation;

		int accepterID = NewGods.data.getPlayerID(accepter);
		Player plrA = Bukkit.getServer().getPlayer(accepter);
		if (accepterID != -1) {
			if (situation.equals("worship")) {
				plrA.sendMessage(ChatColor.GOLD+requester+ " has asked you to become their follower. Type /gaccept to accept or /gdeny to deny.");
			}
			if (situation.equals("marry")) {
				plrA.sendMessage(ChatColor.GOLD+requester + " has proposed to you. Type /gaccept to accept or /gdeny to deny.");
			}
		}

		// remove pending requests to accepter
		ArrayList<AcceptInfo> pendingAccepts = NewGods.data.pendingAccepts;
		for (int i = 0; i < pendingAccepts.size(); i++) {
			if (pendingAccepts.get(i).accepter.equals(accepter)) {
				NewGods.data.pendingAccepts.remove(i);
				break;
			}
		}
	}
	
	public static boolean HasOutgoingRequest(String playerName){
		ArrayList<AcceptInfo> pendingAccepts = NewGods.data.pendingAccepts;
		for(AcceptInfo ai : pendingAccepts){
			if(ai.requester.equals(playerName)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean HasIncommingRequest(String playerName){
		ArrayList<AcceptInfo> pendingAccepts = NewGods.data.pendingAccepts;
		for(AcceptInfo ai : pendingAccepts){
			if(ai.accepter.equals(playerName)){
				return true;
			}
		}
		return false;
	}

}
