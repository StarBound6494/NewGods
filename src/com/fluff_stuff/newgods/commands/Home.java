package com.fluff_stuff.newgods.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.fluff_stuff.newgods.NewGods;

public class Home implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("newgods.home")==false){
				player.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}			
			int plrID=NewGods.data.getPlayerID(player.getName());
			String godName=NewGods.data.playerGod.get(plrID);
			if(godName.equals("null")){
				player.sendMessage(ChatColor.DARK_RED+"You're not in a religion.");	
				return true;
			}
			
			player.sendMessage(ChatColor.AQUA+"Teleporting to your faiths spawn point.");	
			int godID=NewGods.data.getGodID(godName);
			
			String worldName=NewGods.data.godSpawnWorld.get(godID);
			
			double x=NewGods.data.godSpawnX.get(godID)+0.5;
			double y=NewGods.data.godSpawnY.get(godID);
			double z=NewGods.data.godSpawnZ.get(godID)+0.5;
			
			Location teleportPoint=new Location(Bukkit.getWorld(worldName), x, y, z);
			player.teleport(teleportPoint);
			return true;
		}
		return true;
	}

}
