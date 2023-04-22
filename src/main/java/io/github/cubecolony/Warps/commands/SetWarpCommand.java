package io.github.cubecolony.Warps.commands;

import io.github.cubecolony.Warps.WarpsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SetWarpCommand implements CommandExecutor {
    private WarpsManager warpsManager;

    public SetWarpCommand(WarpsManager warpsManager) {
        this.warpsManager = warpsManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage("Please enter a warp to set.");
            return false;
        }
        WarpCommandLogic.setWarp(warpsManager, commandSender, args[0]);
        return true;
    }
}
