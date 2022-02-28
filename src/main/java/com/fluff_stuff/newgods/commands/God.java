package com.fluff_stuff.newgods.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

import com.fluff_stuff.newgods.Data;
import com.fluff_stuff.newgods.InitInterface;
import com.fluff_stuff.newgods.NewGods;
import com.fluff_stuff.newgods.Prefix;

public class God implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("newgods.gods")==false){
				player.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
				return true;
			}
    		NewGods.interfaces.menu.open(player);
			return true;
		}
		return true;
	}

	public static void LeaveGod(Player player) {
		int playerID = NewGods.data.getPlayerID(player.getName());
		String god = NewGods.data.playerGod.get(playerID);
		if (god.equals("null")) {
			player.sendMessage(ChatColor.DARK_RED+"You cant leave a god if you don't have one.");
			return;
		}
		
		NewGods.data.playerGod.set(playerID, "null");
		NewGods.data.playerHolyness.set(playerID, 0);
		NewGods.data.playerGodHappines.set(playerID, 0);
		player.sendMessage(ChatColor.AQUA+"You have abandoned your god. Your faith power is now 0.");
		NewGods.data.savePlayerData(playerID);
		
		int godID = NewGods.data.getGodID(god);
		if (NewGods.data.godLeader.get(godID).equals(player.getName())) {
			Prefix.RemoveLeader(player);
			NewGods.data.godLeader.set(godID, "null");
			if(NewGods.data.godPriests.get(godID).isEmpty()==false){
				NewGods.data.godLeader.set(godID, NewGods.data.godPriests.get(godID).get(0));
				NewGods.data.godPriests.get(godID).remove(NewGods.data.godLeader.get(godID));
			}
		}
		NewGods.data.godBelievers.set(godID, NewGods.data.godBelievers.get(godID) - 1);	
		NewGods.data.godPriests.get(godID).remove(player.getName());
		NewGods.data.saveGodData();
	}

	public static void FollowGod(Player plr, String god) {
		int playerID = NewGods.data.getPlayerID(plr.getName());
		if (NewGods.data.playerGod.get(playerID).equals("null") == false) {
			plr.sendMessage(ChatColor.DARK_RED+"You already worship a god. /gleave to abandon your current god.");
			return;
		}
		int godID = NewGods.data.getGodID(god);
		if (godID == -1) {
			plr.sendMessage(ChatColor.DARK_RED+"This god no longer exists.");
			return;
		}
		NewGods.data.playerGod.set(playerID, god);
		NewGods.data.godBelievers.set(godID, NewGods.data.godBelievers.get(godID) + 1);
		if (NewGods.data.godLeader.get(godID).equals("null")) {
			NewGods.data.godLeader.set(godID, plr.getName());
			plr.sendMessage(ChatColor.AQUA+"You have been made the leader of your faith!");
			InitInterface.menuGodType.open(plr);
			Prefix.AddLeader(plr);
		}
		plr.sendMessage(ChatColor.AQUA+"You are now a follower of " + god+".");
		NewGods.data.savePlayerData(playerID);
		NewGods.data.saveGodData();
	}

	public static void SignGod(SignChangeEvent event, String godName) {
		Player plr = event.getPlayer();		
		if(plr.hasPermission("newgods.create")==false){
			plr.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
			return;
		}
		int playerID = NewGods.data.getPlayerID(plr.getName());
		
		if(godName.equals("null")){
			plr.sendMessage(ChatColor.DARK_RED+"Sorry but that name cant be used.");
			return;
		}
		for(String bannedName : NewGods.bannedGodNames){
			if(godName.toUpperCase().equals(bannedName.toUpperCase())){
				plr.sendMessage(ChatColor.DARK_RED+"Sorry but that name cant be used.");
				return;
			}
		}
		
		int x =event.getBlock().getX();
		int y =event.getBlock().getY();
		int z =event.getBlock().getZ();
		String world = event.getBlock().getWorld().getName();		
		
		if (NewGods.data.getGodID(godName) == -1) {
			NewGods.data.godNames.add(godName);
			NewGods.data.godLeader.add("null");
			NewGods.data.godPriests.add(new ArrayList<String>());
			NewGods.data.godPower.add(0);
			NewGods.data.godBelievers.add(0);
			NewGods.data.godSpawnWorld.add(world);
			NewGods.data.godSpawnX.add(x);
			NewGods.data.godSpawnY.add(y);
			NewGods.data.godSpawnZ.add(z);
			NewGods.data.godType.add("AQUA");
			NewGods.data.saveGodData();
			plr.sendMessage(ChatColor.AQUA+"Congratulations! You successfully created a new god.");
			plr.sendMessage(ChatColor.AQUA+"Religion spawn set at " + event.getBlock().getX() + " " + event.getBlock().getY() + " "
					+ event.getBlock().getZ()+".");
			God.FollowGod(plr, godName);
		}	
		else{
			if(NewGods.data.playerGod.get(playerID).equals("null")){
				FollowGod(plr,godName);
			}
			plr.sendMessage(ChatColor.AQUA+"Created an altar.");
		}		
	}
	
	public static void SetType(Player p, String type){
		p.closeInventory();
		if(p.hasPermission("newgods.type")==false){
			p.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
			return;
		}
		//ChatColor.valueOf("AQUA");
		
		if(type.equals("WHITE") || type.equals("GOLD") || type.equals("LIGHT_PURPLE") || type.equals("AQUA") ||
				type.equals("YELLOW") || type.equals("GREEN") || type.equals("RED") || type.equals("DARK_GRAY") ||
				type.equals("GRAY") || type.equals("DARK_AQUA") || type.equals("DARK_PURPLE") || type.equals("DARK_BLUE") ||
				type.equals("DARK_GREEN") || type.equals("RED") || type.equals("BLACK") || type.equals("DARK_RED")){}
		else{
			return;
		}
		
		
		int pID=NewGods.data.getPlayerID(p.getName());		
		String pGod= NewGods.data.playerGod.get(pID);
		int gID=NewGods.data.getGodID(pGod);
		if(NewGods.data.godLeader.get(gID).equals(p.getName())){
			Prefix.RemoveLeader(p);			
			NewGods.data.godType.set(gID, type);
			p.sendMessage(ChatColor.valueOf(type)+"Changed the god type to "+type+".");
			Prefix.AddLeader(p);
		}
		else{			
			p.sendMessage(ChatColor.DARK_RED+"Sorry but you need to be your gods assigned leader to do this.");
		}
	}
	
	public static void SacrificeItem(Player p){
		int playerID = NewGods.data.getPlayerID(p.getName());
		int godID = NewGods.data.getGodID(NewGods.data.playerGod.get(playerID));
		if(godID==-1){return;}
		if (p.getInventory().getItemInMainHand().isSimilar(new ItemStack(Material.getMaterial(NewGods.godItems.get(godID)),
				NewGods.itemAmountLeft.get(godID)))) {						
			p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
			
			if(p.hasPermission("newgods.doublexp")){
			p.sendMessage(ChatColor.valueOf(NewGods.data.godType.get(godID)) + "Sacrificed item and gained "
					+ NewGods.itemPrayerPoints.get(godID)*2 + " faith power.");
			NewGods.data.playerHolyness.set(playerID,
					NewGods.data.playerHolyness.get(playerID) + NewGods.itemPrayerPoints.get(godID)*2);
			NewGods.data.playerGodHappines.set(playerID,
					NewGods.data.playerGodHappines.get(playerID) + NewGods.itemPrayerPoints.get(godID)*2);
		}
			else{
				p.sendMessage(ChatColor.valueOf(NewGods.data.godType.get(godID)) + "Sacrificed item and gained "
						+ NewGods.itemPrayerPoints.get(godID) + " faith power.");
				NewGods.data.playerHolyness.set(playerID,
						NewGods.data.playerHolyness.get(playerID) + NewGods.itemPrayerPoints.get(godID));
				NewGods.data.playerGodHappines.set(playerID,
						NewGods.data.playerGodHappines.get(playerID) + NewGods.itemPrayerPoints.get(godID));
			}
			NewGods.data.godPower.set(godID, NewGods.data.godPower.get(godID)+NewGods.itemPrayerPoints.get(godID));
			NewGods.itemAmountLeft.set(godID, NewGods.itemAmountLeft.get(godID) - 1);
			if (NewGods.itemAmountLeft.get(godID) <= 0) {
				p.closeInventory();
			} else {
				ItemStack itms = new ItemStack(Material.getMaterial(NewGods.godItems.get(godID)),
						NewGods.itemAmountLeft.get(godID));
				InitInterface.menuSacrifice.setOption(3, itms, NewGods.godItems.get(godID),
						"Sacrifice for " + NewGods.itemPrayerPoints.get(godID) + " faith power.");
				InitInterface.menuSacrifice.open(p);
			}
		} else {
			p.closeInventory();
			p.sendMessage(
					ChatColor.DARK_RED + "You need to be holding the item you want to sacrifice.");
		}
	}

	public static void SacrificeMob(Player p, EntityType e) {
		int playerID = NewGods.data.getPlayerID(p.getName());
		int godID = NewGods.data.getGodID(NewGods.data.playerGod.get(playerID));
		if(godID==-1){return;}
		String mob=NewGods.godMobs.get(godID);
		mob=mob.trim();
		String killedMob=e.toString();
		killedMob=killedMob.trim();
    	if(killedMob.equalsIgnoreCase(mob)){	
    		if(NewGods.mobAmountLeft.get(godID)>0){
    			//
    			if(p.hasPermission("newgods.doublexp")){
    				p.sendMessage(ChatColor.valueOf(NewGods.data.godType.get(godID)) + "Sacrificed mob and gained "
        					+ NewGods.mobPrayerPoints.get(godID)*2 + " faith power.");
        			NewGods.data.playerHolyness.set(playerID,
        					NewGods.data.playerHolyness.get(playerID) + NewGods.mobPrayerPoints.get(godID)*2);
        			NewGods.data.playerGodHappines.set(playerID,
        					NewGods.data.playerGodHappines.get(playerID) + NewGods.mobPrayerPoints.get(godID)*2);
        			NewGods.data.godPower.set(godID, NewGods.data.godPower.get(godID)+NewGods.mobPrayerPoints.get(godID)*2);
    			}
    			else{
    				p.sendMessage(ChatColor.valueOf(NewGods.data.godType.get(godID)) + "Sacrificed mob and gained "
        					+ NewGods.mobPrayerPoints.get(godID) + " faith power.");
        			NewGods.data.playerHolyness.set(playerID,
        					NewGods.data.playerHolyness.get(playerID) + NewGods.mobPrayerPoints.get(godID));
        			NewGods.data.playerGodHappines.set(playerID,
        					NewGods.data.playerGodHappines.get(playerID) + NewGods.mobPrayerPoints.get(godID));
        			NewGods.data.godPower.set(godID, NewGods.data.godPower.get(godID)+NewGods.mobPrayerPoints.get(godID));
    			}
    			
    			NewGods.mobAmountLeft.set(godID, NewGods.mobAmountLeft.get(godID) - 1);
    			//
    		}
    	}		
	}

}
