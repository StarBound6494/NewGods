package com.fluff_stuff.newgods.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fluff_stuff.newgods.AcceptInfo;
import com.fluff_stuff.newgods.NewGods;

public class Deny implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("newgods.deny")==false){
				player.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}
			ArrayList<AcceptInfo> pendingAccepts = NewGods.data.pendingAccepts;
			for(AcceptInfo a : pendingAccepts){
				if(a.accepter.equals(player.getName())){
					NewGods.data.pendingAccepts.remove(a);
					player.sendMessage(ChatColor.AQUA+"Denied "+a.situation+" request.");
					return true;
				}
			}
			player.sendMessage(ChatColor.AQUA+"Couldn't find any requests.");
			return true;
		}
		return true;
	}

}
