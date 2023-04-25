package io.github.cubecolony.Warps.commands;

import io.github.cubecolony.Warps.WarpsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RemoveWarpCommand implements CommandExecutor {
    private WarpsManager warpsManager;

    public RemoveWarpCommand(WarpsManager warpsManager) {
        this.warpsManager = warpsManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(Component.text("Please enter a warp to remove.")
                    .style(Style.style().color(NamedTextColor.RED).build()));
            return true;
        }
        WarpCommandLogic.removeWarp(warpsManager, commandSender, args[0]);
        return true;
    }
}
