package com.fluff_stuff.newgods.event.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.fluff_stuff.newgods.NewGods;
import com.fluff_stuff.newgods.Prefix;

public class PlayerQuit implements Listener {
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		NewGods.data.unloadPlayer(NewGods.data.getPlayerID(event.getPlayer().getName()));
		Prefix.RemovePlayer(event.getPlayer().getName());
	}
}

