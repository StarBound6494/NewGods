package com.fluff_stuff.newgods.event.sign;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.fluff_stuff.newgods.NewGods;
import com.fluff_stuff.newgods.commands.God;

public class SignChanged implements Listener {

	@EventHandler
	public void eventSignChanged(SignChangeEvent event) {
		String title = event.getLine(0);
		if (title.equalsIgnoreCase("god") || title.equalsIgnoreCase("gods")) {
			String godName = event.getLine(1);
			if (godName.length() > 0) {
				if (godName.equals("null") == false && godName.contains("/") == false
						&& godName.contains(".") == false) {
					godName = godName.trim();
					for(int i=0;i<NewGods.data.godNames.size();i++){
						if(godName.equalsIgnoreCase(NewGods.data.godNames.get(i))){
							godName=NewGods.data.godNames.get(i);
							break;
						}
					}
					Player p =event.getPlayer();
					String playerGod=NewGods.data.playerGod.get(NewGods.data.getPlayerID(p.getName()));
					if(playerGod.equals(godName)==false && playerGod.equals("null")==false){
						p.sendMessage(ChatColor.DARK_RED+"You can only make alters for your god.");
						return;
					}
					event.setLine(0, "=========");
					event.setLine(1, "Altar of");
					event.setLine(2, godName);
					event.setLine(3, "=========");
					God.SignGod(event, godName);
				}
			}

		}
	}



}
