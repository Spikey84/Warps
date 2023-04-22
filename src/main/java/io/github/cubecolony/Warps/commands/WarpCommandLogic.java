package io.github.cubecolony.Warps.commands;

import io.github.cubecolony.Warps.Warp;
import io.github.cubecolony.Warps.WarpsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WarpCommandLogic {

    public static boolean setWarp(WarpsManager warpsManager, CommandSender sender, String arg) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        Location location = player.getLocation();

        String name = arg;

        Warp warp = new Warp(UUID.randomUUID(), arg, Bukkit.getServer().getName(), location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), (int) location.getPitch(),(int) location.getYaw());

        player.sendMessage("Creating warp %s.".formatted(warp.getName()));
        warpsManager.addWarp(warp);
        return true;
    }

    public static boolean travelWarp(WarpsManager warpsManager, CommandSender sender, String arg) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        Location location = player.getLocation();

        String name = arg;

        Warp warp = null;

        for (Warp w : warpsManager.getWarps()) {
            if (w.getName().equals(name)) warp = w;
        }

        if (warp == null)  {
            player.sendMessage("This warps does not exist.");
            return false;
        }

        player.sendMessage("Teleporting to warp %s.".formatted(warp.getName()));
        warpsManager.travelToWarp(warp, player);

        return true;
    }

    public static boolean removeWarp(WarpsManager warpsManager, CommandSender sender, String arg) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        String name = arg;

        Warp warp = null;

        for (Warp w : warpsManager.getWarps()) {
            if (w.getName().equals(name)) warp = w;
        }

        if (warp == null)  {
            player.sendMessage("This warps does not exist.");
            return false;
        }

        player.sendMessage("Warp %s has been removed.".formatted(warp.getName()));
        warpsManager.removeWarp(warp);
        return true;
    }
}
