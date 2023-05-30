package me.cookie.creeperconfetti.events;

import me.cookie.creeperconfetti.CreeperConfetti;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.*;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

public class CreeperExplodeListener implements Listener {
    @EventHandler
    public void onCreeperExplode(EntityExplodeEvent event){
    	double random = ThreadLocalRandom.current().nextDouble() * 100;

    	// if random not less than confetti_chance, stop
    	if (random >= CreeperConfetti.getInstance().getConfig().getDouble("confetti_chance"))
            return;

        if(event.getEntityType().equals(EntityType.CREEPER)){
            event.setCancelled(true);

            Creeper creeper = (Creeper)event.getEntity();
            Location location = creeper.getLocation();
            location = location.add(new Vector(0, 1, 0));

            Firework firework = (Firework)creeper.getWorld().spawnEntity(location, EntityType.FIREWORK);

            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.addEffects((List<FireworkEffect>) CreeperConfetti.getInstance().getConfig().get("confetti_effect"));
            fireworkMeta.setPower(0);
            firework.setFireworkMeta(fireworkMeta);

            location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 2, 1);

            Bukkit.getScheduler().scheduleSyncDelayedTask(CreeperConfetti.getInstance(), firework::detonate, 1);
        }
    }
}