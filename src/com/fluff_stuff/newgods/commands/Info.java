package com.fluff_stuff.newgods.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fluff_stuff.newgods.NewGods;

public class Info implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("newgods.info")==false){
				player.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}
			int playerID = NewGods.data.getPlayerID(player.getName());
			String playerGod =NewGods.data.playerGod.get(playerID);
			if(playerGod.equals("null")){
				player.sendMessage(ChatColor.DARK_RED+"You dont follow a god!");
				return true;
			}
			int godID = NewGods.data.getGodID(playerGod);
			player.sendMessage(ChatColor.AQUA+"You're a woshiper of " + playerGod +".");
			player.sendMessage(ChatColor.AQUA+"Your faith power is " + NewGods.data.playerHolyness.get(playerID)+".");
			player.sendMessage(ChatColor.AQUA+playerGod+" has "+NewGods.data.godBelievers.get(godID)+" followers.");	
			player.sendMessage(ChatColor.AQUA+playerGod+" has a total faith power of "+NewGods.data.godPower.get(godID)+".");	
			return true;
		}
		return true;
	}
	
}
