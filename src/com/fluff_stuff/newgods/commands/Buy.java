package com.fluff_stuff.newgods.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fluff_stuff.newgods.InitInterface;
import com.fluff_stuff.newgods.NewGods;

public class Buy implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("newgods.buy")==false){
				p.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}
			int pID=NewGods.data.getPlayerID(p.getName());
			String god=NewGods.data.playerGod.get(pID);
			int gID=NewGods.data.getGodID(god);
			if (gID == -1) {
				p.closeInventory();
				p.sendMessage(ChatColor.DARK_RED + "You need a god to do this.");
				return true;
			}
			InitInterface.updateBuyMenu(pID);
			InitInterface.menuBuy.open(p);
		}
		return true;
	}
}
