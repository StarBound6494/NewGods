package com.fluff_stuff.newgods.event.block;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.fluff_stuff.newgods.NewGods;
import com.fluff_stuff.newgods.commands.God;

public class Interact implements Listener{
	
	static HashMap<String,Integer> plrs = new HashMap<String,Integer>();
	public static int timeBetweenPrays=600;
	
	@EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();        
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){       
            if (e.getClickedBlock().getState() instanceof Sign) { 
            	Sign sign=(Sign) e.getClickedBlock().getState();
            	if(sign.getLine(0).equals("=========")){
            		if(sign.getLine(1).equals("Altar of")){
            			if(p.hasPermission("newgods.pray")==false){
            				p.sendMessage(ChatColor.DARK_RED+"Sorry but you're not permitted to do this.");
            				return;
            			}
            			String godName=sign.getLine(2);
            			int godID=NewGods.data.getGodID(godName);
            			if(godID!=-1){
            				String name=p.getName();
            				if(plrs.containsKey(name)){
            					long epoch = System.currentTimeMillis();
            					int time=(int) (epoch / 1000);
            					if((plrs.get(name)>time-timeBetweenPrays && p.hasPermission("newgods.halfpraytime")==false)){
            						p.sendMessage(ChatColor.DARK_RED+"Wait another "+(timeBetweenPrays-(time-plrs.get(name)))+" seconds.");
            						return;
            					}
            					if((p.hasPermission("newgods.halfpraytime") && plrs.get(name)>time-timeBetweenPrays/2)){
            						p.sendMessage(ChatColor.DARK_RED+"Wait another "+(timeBetweenPrays/2-(time-plrs.get(name)))+" seconds.");
            						return;
            					}
            				}
            				int playerID=NewGods.data.getPlayerID(name);
            				String playerGod = NewGods.data.playerGod.get(playerID);
            				if(playerGod.equals(godName)){
            					if(p.hasPermission("newgods.doublexp")){
                					NewGods.data.godPower.set(godID, NewGods.data.godPower.get(godID)+20);
                					NewGods.data.playerHolyness.set(playerID, NewGods.data.playerHolyness.get(playerID)+20);
                					NewGods.data.playerGodHappines.set(playerID, NewGods.data.playerGodHappines.get(playerID)+20);
                					p.sendMessage(ChatColor.AQUA+"Prayed to "+godName+" and gained 20 faith power.");       						
            					}
            					else{
                  					NewGods.data.godPower.set(godID, NewGods.data.godPower.get(godID)+10);
                					NewGods.data.playerHolyness.set(playerID, NewGods.data.playerHolyness.get(playerID)+10);
                					NewGods.data.playerGodHappines.set(playerID, NewGods.data.playerGodHappines.get(playerID)+10);
                					p.sendMessage(ChatColor.AQUA+"Prayed to "+godName+" and gained 10 faith power.");
            					}
            					long epoch = System.currentTimeMillis();
            					int time=(int) (epoch / 1000);
            					plrs.put(name, time);
            					return;            					
            				}else{
            					if(playerGod.equals("null")){
            						God.FollowGod(p,godName);
            						return;
            					}
            					p.sendMessage(ChatColor.DARK_RED+"This isn't an altar for your god. Type /gleave to leave your god.");
            					return;
            				}            				
            			}
            		}
            	}
            }
        }
	}
}
