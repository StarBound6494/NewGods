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
import com.fluff_stuff.newgods.Prefix;

public class Accept implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("newgods.accept")==false){
				player.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}
			ArrayList<AcceptInfo> pendingAccepts = NewGods.data.pendingAccepts;
			for (AcceptInfo a : pendingAccepts) {
				if (a.accepter.equals(player.getName())) {
					NewGods.data.pendingAccepts.remove(a);
					if (a.situation.equals("marry")) {
						marry(player, a);
					}
					if (a.situation.equals("worship")) {
						God.FollowGod(player, a.requester);
					}
					return true;
				}
			}
			player.sendMessage(ChatColor.DARK_RED+"Couldn't find any requests.");
			return true;
		}
		return true;
	}

	private void marry(Player p, AcceptInfo a) {
		int requesterID = NewGods.data.getPlayerID(a.requester);
		int accepterID = NewGods.data.getPlayerID(a.accepter);
		if (requesterID == -1 || accepterID == -1) {
			p.sendMessage(ChatColor.DARK_RED+"The other player must be online sorry.");
			return;
		}
		NewGods.data.playerPartner.set(requesterID, a.accepter);
		NewGods.data.playerPartner.set(accepterID, a.requester);
		NewGods.data.savePlayerData(requesterID);
		NewGods.data.savePlayerData(accepterID);
		Bukkit.broadcastMessage(ChatColor.GOLD+"Congratulations to " + a.requester + " and " + a.accepter + " on getting married!");

		Player plrA = Bukkit.getServer().getPlayer(a.requester);
		Player plrB = Bukkit.getServer().getPlayer(a.accepter);
		String a1 = plrA.getDisplayName();
		String b1 = plrB.getDisplayName();
		
		Prefix.AddHeart(plrA);
		Prefix.AddHeart(plrB);
	}

}
