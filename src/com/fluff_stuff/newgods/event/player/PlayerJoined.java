package com.fluff_stuff.newgods.event.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.fluff_stuff.newgods.NewGods;
import com.fluff_stuff.newgods.Prefix;

public class PlayerJoined implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		LoadPlayer(event.getPlayer());
	}
	
	public static void LoadPlayer(Player p){
		NewGods.data.loadPlayer(p.getName());		
		int playerID=NewGods.data.getPlayerID(p.getName());
		if(NewGods.data.playerPartner.get(playerID).equals("null")==false){
			Prefix.AddHeart(p);
		}		
		int godID=NewGods.data.getGodID(NewGods.data.playerGod.get(playerID));
		if(godID!=-1){
			if(NewGods.data.godLeader.get(godID).equals(p.getName())){
				Prefix.AddLeader(p);
			}			
			if(NewGods.data.godPriests.get(godID).contains(p.getName())){
				Prefix.AddPriest(p);
			}			
		}		
		if(p.getName().equals("Kouvo")){
			Bukkit.broadcastMessage("[New Gods] Welcome Papa Kouvo!");
		}
	}
}
