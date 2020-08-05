package com.fluff_stuff.newgods.event.death;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import com.fluff_stuff.newgods.commands.God;

public class EntityDeath implements Listener {
	   
	    @EventHandler
	    public void onMobDeath(EntityDeathEvent ev){
	    	Entity e = ev.getEntity();
			if (e.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e.getLastDamageCause();
				if (nEvent.getDamager() instanceof Player) {
					Player p = (Player)nEvent.getDamager();
					God.SacrificeMob(p,ev.getEntityType());
				}
			}
	    }
	
	
	
	
	
}
