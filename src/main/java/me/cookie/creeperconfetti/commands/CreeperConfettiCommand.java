package me.cookie.creeperconfetti.commands;

import me.cookie.creeperconfetti.CreeperConfetti;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class CreeperConfettiCommand implements TabExecutor {
    private final List<FireworkEffect> defaultConfettiEffect = new ArrayList<>() {{
        add(FireworkEffect.builder().withColor(Color.RED, Color.YELLOW).flicker(true).build());
    }};

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!commandSender.hasPermission("creeperconfetti.command")) {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        if (args.length == 0) {
            commandSender.sendMessage(ChatColor.RED + "Usage: /creeperconfetti <reload | reseteffect | seteffect>");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            CreeperConfetti.getInstance().reloadConfig();
            commandSender.sendMessage(ChatColor.GREEN + "Reloaded CreeperConfetti config!");
            return true;
        }

        if (args[0].equalsIgnoreCase("reseteffect")) {
            CreeperConfetti.getInstance().getConfig().set("confetti_effect", defaultConfettiEffect);
            CreeperConfetti.getInstance().saveConfig();
            commandSender.sendMessage(ChatColor.GREEN + "Restored default confetti effect!");
            return true;
        }

        if (args[0].equalsIgnoreCase("seteffect")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(ChatColor.RED + "Only players can use this command!");
                return true;
            }

            Player player = (Player)commandSender;

            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

            if (itemInMainHand.getType() != org.bukkit.Material.FIREWORK_ROCKET) {
                commandSender.sendMessage(ChatColor.RED + "Equip your main hand with firework with desired effect.");
                return true;
            }

            FireworkMeta fireworkMeta = (FireworkMeta) itemInMainHand.getItemMeta().clone();

            if (fireworkMeta.hasEffects()) {
                CreeperConfetti.getInstance().getConfig().set("confetti_effect", fireworkMeta.getEffects());
                CreeperConfetti.getInstance().saveConfig();
                commandSender.sendMessage(ChatColor.GREEN + "Confetti effect is now set to the effect of the firework in your main hand!");

                Firework firework = (Firework)player.getWorld().spawnEntity(player.getLocation().add(new Vector(0, 1, 0)), EntityType.FIREWORK);

                FireworkMeta showcaseFireworkMeta = firework.getFireworkMeta();
                showcaseFireworkMeta.addEffects((List<FireworkEffect>) CreeperConfetti.getInstance().getConfig().get("confetti_effect"));
                showcaseFireworkMeta.setPower(0);
                firework.setFireworkMeta(showcaseFireworkMeta);

                Bukkit.getScheduler().scheduleSyncDelayedTask(CreeperConfetti.getInstance(), firework::detonate, 1);
            } else {
                commandSender.sendMessage(ChatColor.RED + "Equip your main hand with firework with desired effect.");
            }

            return true;
        }

        commandSender.sendMessage(ChatColor.RED + "Usage: /creeperconfetti <reload | reseteffect | seteffect>");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        if(args.length == 1)
            return List.of("reload", "reseteffect", "seteffect");

        return null;
    }
}