package com.fluff_stuff.newgods.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import com.fluff_stuff.newgods.NewGods;
import com.fluff_stuff.newgods.Prefix;

public class PlayerChat implements Listener {

	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		Prefix.ChatUpdate(event.getPlayer());	
	}
}
