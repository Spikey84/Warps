package io.github.cubecolony.Warps;

import io.github.cubecolony.Warps.commands.RemoveWarpCommand;
import io.github.cubecolony.Warps.commands.SetWarpCommand;
import io.github.cubecolony.Warps.commands.WarpCommand;
import io.github.cubecolony.Warps.utils.SchedulerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private WarpsManager warpsManager;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        SchedulerUtils.setPlugin(this);
        DatabaseManager.initDatabase(this);
        SchedulerUtils.runDatabase((DatabaseManager::createTables));

        warpsManager = new WarpsManager();

        getCommand("warp").setExecutor(new WarpCommand(warpsManager));
        getCommand("setwarp").setExecutor(new SetWarpCommand(warpsManager));
        getCommand("removewarp").setExecutor(new RemoveWarpCommand(warpsManager));
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public WarpsManager getWarpsManager() {
        return warpsManager;
    }
}
