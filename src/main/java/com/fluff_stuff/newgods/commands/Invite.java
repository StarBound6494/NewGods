package com.fluff_stuff.newgods.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fluff_stuff.newgods.AcceptInfo;
import com.fluff_stuff.newgods.NewGods;

public class Invite implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			/////
			Player player = (Player) sender;
			if(player.hasPermission("newgods.invite")==false){
				player.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}
			if (AcceptInfo.HasOutgoingRequest(player.getName())) {
				player.sendMessage(ChatColor.DARK_RED+"You already have an outgoing request.");
				return true;
			}

			int playerID = NewGods.data.getPlayerID(player.getName());
			if (NewGods.data.playerGod.get(playerID).equals("null")) {
				player.sendMessage(ChatColor.DARK_RED+"You need to have a god to invite someone else.");
				return true;
			}

			if (args != null) {
				if (args.length > 0) {
					String otherPlayer = args[0];
					if (otherPlayer != null) {
						if (otherPlayer.isEmpty() == false) {
							if (otherPlayer.equals(player.getName())) {
								player.sendMessage(ChatColor.DARK_RED+"You cant invite yourself.");
								return true;
							}
							int otherPlayerID = NewGods.data.getPlayerID(otherPlayer);
							if (otherPlayerID == -1) {
								player.sendMessage(ChatColor.DARK_RED+"That player isn't currently online.");
								return true;
							}

							if (AcceptInfo.HasIncommingRequest(otherPlayer)) {
								player.sendMessage(ChatColor.DARK_RED+"That player already has a pending request.");
								return true;
							}

							NewGods.data.pendingAccepts
									.add(new AcceptInfo(otherPlayer, NewGods.data.playerGod.get(playerID), "worship"));
							player.sendMessage(ChatColor.AQUA+"Sent request to " + otherPlayer + " to join your religion.");
							return true;

						}
					}
				}
			}
			player.sendMessage(ChatColor.DARK_RED+"The player you tried sending an invite to could not be found.");
			/////
			return true;
		}
		return true;
	}

}
