package me.cookiemonster.creeperconfetti;

import me.cookiemonster.creeperconfetti.events.CreeperExplodeListener;
import me.cookiemonster.creeperconfetti.metrics.MetricsLite;
import org.bukkit.plugin.java.JavaPlugin;

public class CreeperConfetti extends JavaPlugin {
    private static CreeperConfetti instance;

    @Override
    public void onEnable() {
    	saveDefaultConfig();
        instance = this;
        getServer().getPluginManager().registerEvents(new CreeperExplodeListener(), this);
        MetricsLite metrics = new MetricsLite(this, 9232);
    }

    public static CreeperConfetti getInstance(){
        return instance;
    }

}
