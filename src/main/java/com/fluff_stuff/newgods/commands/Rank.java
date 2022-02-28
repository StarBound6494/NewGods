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

public class Rank implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("newgods.rank")==false){
				p.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}
			int playerID = NewGods.data.getPlayerID(p.getName());
			String god = NewGods.data.playerGod.get(playerID);
			int godID = NewGods.data.getGodID(god);
			if (godID==-1) {
				p.sendMessage(ChatColor.DARK_RED+"You need a god to do this command.");
				return true;
			}
			
			if (NewGods.data.godLeader.get(godID).equals(p.getName()) == false) {
				p.sendMessage(ChatColor.DARK_RED+"You need to be your gods assigned leader to do this.");
				return true;
			}

			if (args != null) {
				if (args.length > 0) {
					String rankedPlayer = args[0];
					if (rankedPlayer != null) {
						if (rankedPlayer.isEmpty() == false) {
							if (rankedPlayer.equals(p.getName())) {
								p.sendMessage(ChatColor.DARK_RED+"You cant rank yourself.");
								return true;
							}

							int rankID = NewGods.data.getPlayerID(rankedPlayer);
							if (rankID == -1) {
								p.sendMessage(ChatColor.DARK_RED+"Couldn't find that player sorry.");
								return true;
							}

							if (NewGods.data.playerGod.get(rankID).equals(god) == false) {
								p.sendMessage(ChatColor.DARK_RED+"That player does not follow the same god as you.");
								return true;
							}

							if (NewGods.data.godPriests.get(godID).contains(rankedPlayer)) {
								NewGods.data.godLeader.set(godID,rankedPlayer);								
								if(NewGods.data.godPriests.contains(p.getName())==false){
									NewGods.data.godPriests.get(godID).add(p.getName());
								}								
								Player rPlr = Bukkit.getServer().getPlayer(rankedPlayer);								
								Prefix.RemoveLeader(p);
								Prefix.AddPriest(p);
								Prefix.RemovePriest(rPlr);
								Prefix.AddLeader(rPlr);
								rPlr.sendMessage(ChatColor.AQUA+"You have been made your gods newly assigned leader!");
								p.sendMessage(ChatColor.AQUA+"You have ranked "+rankedPlayer+" from priest to leader. You are now a priest.");
								NewGods.data.saveGodData();								
								return true;
							}

							NewGods.data.godPriests.get(godID).add(rankedPlayer);
							Player rPlr = Bukkit.getServer().getPlayer(rankedPlayer);
							Prefix.AddPriest(rPlr);
							rPlr.sendMessage(ChatColor.AQUA+"You are now a priest!");
							p.sendMessage(ChatColor.AQUA+"You have ranked "+rankedPlayer+".");
							NewGods.data.saveGodData();
							return true;
						}
					}
				}
			}
			p.sendMessage(ChatColor.DARK_RED+"Could not rank that player.");
			return true;

		}
		return true;
	}
}
