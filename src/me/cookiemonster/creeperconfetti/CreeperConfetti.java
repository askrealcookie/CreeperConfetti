package me.cookiemonster.creeperconfetti;

import me.cookiemonster.creeperconfetti.events.CreeperExplodeListener;
import org.bukkit.plugin.java.JavaPlugin;

public class CreeperConfetti extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CreeperExplodeListener(), this);
    }
}
