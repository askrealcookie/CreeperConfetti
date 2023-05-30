package me.cookie.creeperconfetti.events;

import me.cookie.creeperconfetti.CreeperConfetti;

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
    public void onCreeperExplode(EntityExplodeEvent e){
    	double random = ThreadLocalRandom.current().nextDouble() * 100;
    	// if random not less than confetti_chance, stop
    	if (random >= CreeperConfetti.getInstance().getConfig().getDouble("confetti_chance")) return;
        if(e.getEntityType().equals(EntityType.CREEPER)){
            e.setCancelled(true);
            Creeper c = (Creeper)e.getEntity();
            Location loc = c.getLocation();
            loc = loc.add(new Vector(0, 1, 0));
            Firework fw = (Firework)c.getWorld().spawnEntity(loc, EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            fwm.setPower(0);
            fwm.addEffect(FireworkEffect.builder().withColor(Color.RED, Color.YELLOW).flicker(true).build());
            fw.setFireworkMeta(fwm);
            loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 2, 1);
            Bukkit.getScheduler().scheduleSyncDelayedTask(CreeperConfetti.getInstance(), new Runnable() {
                @Override
                public void run() {
                    fw.detonate();
                }
            }, 1);
        }
    }
}