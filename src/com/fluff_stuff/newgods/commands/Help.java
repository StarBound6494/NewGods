package com.fluff_stuff.newgods.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Help implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("newgods.help")==false){
				player.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}
			player.sendMessage(ChatColor.GOLD+"===Leader Commands===");
			player.sendMessage(ChatColor.AQUA+"/grank <player>");
			player.sendMessage(ChatColor.AQUA+"/gdemote <player>");
			player.sendMessage(ChatColor.AQUA+"/gtype");
			player.sendMessage(ChatColor.GOLD+"===Priest Commands===");
			player.sendMessage(ChatColor.AQUA+"/gsethome");
			player.sendMessage(ChatColor.AQUA+"/ginvite <player>");
			player.sendMessage(ChatColor.GOLD+"===Marriage Commands===");
			player.sendMessage(ChatColor.AQUA+"/gmarry <player>");
			player.sendMessage(ChatColor.AQUA+"/gdivorce");
			player.sendMessage(ChatColor.AQUA+"/gmarrytp");
			player.sendMessage(ChatColor.AQUA+"/gmarrygift");
			player.sendMessage(ChatColor.GOLD+"===Regular Commands===");
			player.sendMessage(ChatColor.AQUA+"/gods");
			player.sendMessage(ChatColor.AQUA+"/gsacrifice");
			player.sendMessage(ChatColor.AQUA+"/glist");
			player.sendMessage(ChatColor.AQUA+"/ginfo");
			player.sendMessage(ChatColor.AQUA+"/gleave");
			player.sendMessage(ChatColor.AQUA+"/ghome");
			player.sendMessage(ChatColor.GOLD+
					"To create a new god place a sign and write 'god' on the first line and your gods name on the second line.");
			return true;
		}
		return true;
	}

}
