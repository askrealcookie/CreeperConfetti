package me.cookie.creeperconfetti;

import me.cookie.creeperconfetti.commands.CreeperConfettiCommand;
import me.cookie.creeperconfetti.events.CreeperExplodeListener;
import me.cookie.creeperconfetti.metrics.MetricsLite;
import org.bukkit.plugin.java.JavaPlugin;

public class CreeperConfetti extends JavaPlugin {
    private static CreeperConfetti instance;

    @Override
    public void onEnable() {
    	saveDefaultConfig();
        instance = this;

        getServer().getPluginManager().registerEvents(new CreeperExplodeListener(), this);
        getCommand("creeperconfetti").setExecutor(new CreeperConfettiCommand());

        MetricsLite metrics = new MetricsLite(this, 9232);
    }

    public static CreeperConfetti getInstance(){
        return instance;
    }

}
