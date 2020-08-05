package com.fluff_stuff.newgods.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fluff_stuff.newgods.NewGods;

public class SetHome implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("newgods.sethome")==false){
				p.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}
			int playerID = NewGods.data.getPlayerID(p.getName());
			String god = NewGods.data.playerGod.get(playerID);			
			int godID = NewGods.data.getGodID(god);
			
			if(godID==-1){
				p.sendMessage(ChatColor.DARK_RED+"You need a god to do this command.");
			}
			
			if(NewGods.data.godLeader.get(godID).equals(p.getName())==false && NewGods.data.godPriests.get(godID).contains(p.getName())==false){
				p.sendMessage(ChatColor.DARK_RED+"You need to be a priest or the leader of your faith to do this command.");
			}
			
			//do the thing
			NewGods.data.godSpawnX.set(godID, p.getLocation().getBlockX());
			NewGods.data.godSpawnY.set(godID, p.getLocation().getBlockY());
			NewGods.data.godSpawnZ.set(godID, p.getLocation().getBlockZ());
			NewGods.data.godSpawnWorld.set(godID, p.getLocation().getWorld().getName());
			p.sendMessage(ChatColor.AQUA+"Your faiths spawn has been set at " + p.getLocation().getBlock().getX() + " " + p.getLocation().getBlock().getY() + " "
					+ p.getLocation().getBlock().getZ()+".");
			return true;
			
		}
		return true;
	}
	
}
