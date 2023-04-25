package io.github.cubecolony.Warps.commands;

import io.github.cubecolony.Warps.WarpsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
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
            commandSender.sendMessage(Component.text("Please enter a warp to set.")
                    .style(Style.style().color(NamedTextColor.RED).build()));
            return true;
        }
        WarpCommandLogic.setWarp(warpsManager, commandSender, args[0]);
        return true;
    }
}
