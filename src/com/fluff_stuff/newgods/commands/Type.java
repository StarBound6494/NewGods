package com.fluff_stuff.newgods.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fluff_stuff.newgods.InitInterface;
import com.fluff_stuff.newgods.NewGods;

public class Type implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("newgods.type")==false){
				player.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}
			InitInterface.menuGodType.open(player);
			return true;
		}
		return true;
	}

}
