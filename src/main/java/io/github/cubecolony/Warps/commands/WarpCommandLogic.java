package io.github.cubecolony.Warps.commands;

import io.github.cubecolony.Warps.Warp;
import io.github.cubecolony.Warps.WarpsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WarpCommandLogic {

    public static boolean setWarp(WarpsManager warpsManager, CommandSender sender, String name) {
        if (!(sender instanceof Player player)) return false;
        Location location = player.getLocation();
        if (!player.hasPermission("spikey.warps.manage")) {
            sender.sendMessage(Component.text("You don't have permission to do that.")
                    .style(Style.style().color(NamedTextColor.RED).build()));
            return true;
        }

        Warp warp = new Warp(UUID.randomUUID(), name, Bukkit.getServer().getName(), location.getWorld().getUID(),
                location.getBlockX(), location.getBlockY(), location.getBlockZ(), (int) location.getPitch(),(int) location.getYaw());

        player.sendMessage(Component.text("Creating warp ").style(Style.style(NamedTextColor.YELLOW))
                .append(Component.text(warp.getName()).style(Style.style(NamedTextColor.YELLOW)))
                .append(Component.text(".").style(Style.style(NamedTextColor.YELLOW))));

        warpsManager.addWarp(warp);
        return true;
    }

    public static boolean travelWarp(WarpsManager warpsManager, CommandSender sender, String arg) {
        if (!(sender instanceof Player player)) return false;
        Warp warp = null;

        for (Warp w : warpsManager.getWarps()) {
            if (w.getName().equals(arg)) warp = w;
        }

        if (warp == null)  {
            sender.sendMessage(Component.text("That warp does not exist.")
                    .style(Style.style().color(NamedTextColor.RED).build()));
            return false;
        }

        player.sendMessage(Component.text("Teleporting to warp %s.".formatted(warp.getName()))
                .style(Style.style(NamedTextColor.YELLOW)));
        warpsManager.travelToWarp(warp, player);

        return true;
    }

    public static boolean removeWarp(WarpsManager warpsManager, CommandSender sender, String name) {
        if (!(sender instanceof Player player)) return false;
        if (!player.hasPermission("spikey.warps.manage")) {
            sender.sendMessage(Component.text("You don't have permission to do that.")
                    .style(Style.style().color(NamedTextColor.RED).build()));
            return true;
        }

        Warp warp = null;

        for (Warp w : warpsManager.getWarps()) {
            if (w.getName().equals(name)) warp = w;
        }

        if (warp == null)  {
            sender.sendMessage(Component.text("That warp does not exist.")
                    .style(Style.style().color(NamedTextColor.RED).build()));
            return false;
        }

        player.sendMessage(Component.text("Warp %s has been removed.".formatted(warp.getName()))
                .style(Style.style(NamedTextColor.YELLOW)));
        warpsManager.removeWarp(warp);
        return true;
    }
}
