package com.fluff_stuff.newgods.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fluff_stuff.newgods.AcceptInfo;
import com.fluff_stuff.newgods.NewGods;

public class Marry implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("newgods.marry")==false){
				player.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}
			if(AcceptInfo.HasOutgoingRequest(player.getName())){
				player.sendMessage(ChatColor.DARK_RED+"You already have an outgoing request.");
				return true;
			}
			
			if(AcceptInfo.HasIncommingRequest(player.getName())){
				player.sendMessage(ChatColor.DARK_RED+"You already have a marrige request. type /gdeny to deny it.");
				return true;
			}
			
			if (args != null) {
				if (args.length > 0) {
					String partner = args[0];
					if (partner != null) {
						if (partner.isEmpty() == false) {
							if (partner.equals(player.getName())) {
								player.sendMessage(ChatColor.DARK_RED+"You cant marry yourself!");
								return true;
							}
							int partnerID = NewGods.data.getPlayerID(partner);
							if (partnerID == -1) {
								player.sendMessage(ChatColor.DARK_RED+"Sorry, that player cant be found.");
								return true;
							}
							Player marryPlayer = Bukkit.getServer().getPlayer(partner);
							if (marryPlayer == null) {
								player.sendMessage(ChatColor.DARK_RED+"Sorry, that player cant be found.");
								return true;
							}
							if(NewGods.data.playerPartner.get(partnerID).equals("null")==false){
								player.sendMessage(ChatColor.DARK_RED+"That player is already married.");
								return true;
							}
							player.sendMessage(ChatColor.GOLD+"Sending request to " + partner);
							NewGods.data.pendingAccepts
									.add(new AcceptInfo(marryPlayer.getName(), player.getName(), "marry"));
							return true;
						}
					}
				}
			}
			player.sendMessage(ChatColor.DARK_RED+"Include the person you want to marry /gmarry <player name>");
			return true;
		}
		return true;
	}

}
