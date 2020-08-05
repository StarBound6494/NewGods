package com.fluff_stuff.newgods.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fluff_stuff.newgods.NewGods;
import com.fluff_stuff.newgods.Prefix;

public class Divorce implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("newgods.divorce")==false){
				p.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}
			int playerID = NewGods.data.getPlayerID(p.getName());
			String partner = NewGods.data.playerPartner.get(playerID);
			if (partner.equals("null")) {
				p.sendMessage(ChatColor.DARK_RED+"you're not married");
				return true;
			}

			int partnerID = NewGods.data.getPlayerID(partner);

			boolean partnerOnline = true;
			if (partnerID == -1) {
				NewGods.data.loadPlayer(partner);
				partnerOnline = false;
				partnerID = NewGods.data.getPlayerID(partner);
			}

			NewGods.data.playerPartner.set(playerID, "null");
			NewGods.data.playerPartner.set(partnerID, "null");

			NewGods.data.savePlayerData(playerID);
			NewGods.data.savePlayerData(partnerID);

			Player otherPlayer = Bukkit.getServer().getPlayer(partner);
			if (partnerOnline == false) {
				NewGods.data.unloadPlayer(partnerID);
			} else {
				Prefix.RemoveHeart(otherPlayer);
				otherPlayer.sendMessage(ChatColor.AQUA+"You got divorced.");
			}
			Prefix.RemoveHeart(p);
			p.sendMessage(ChatColor.AQUA+"You got divorced.");
			
		}
		return true;
	}



}
