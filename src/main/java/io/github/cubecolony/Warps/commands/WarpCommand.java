package io.github.cubecolony.Warps.commands;

import io.github.cubecolony.Warps.Main;
import io.github.cubecolony.Warps.WarpsManager;
import io.github.cubecolony.Warps.inventory.WarpsInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpCommand implements CommandExecutor {
    private WarpsManager warpsManager;
    private Main plugin;

    public WarpCommand(WarpsManager warpsManager, Main plugin) {
        this.warpsManager = warpsManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            Player player = (Player) commandSender;
            new WarpsInventory("Warps", player, warpsManager, 1).open(player);
            return true;
        }
        WarpCommandLogic.travelWarp(warpsManager, commandSender, args[0]);
        return true;
    }
}
