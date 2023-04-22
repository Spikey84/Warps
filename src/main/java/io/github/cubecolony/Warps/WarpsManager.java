package io.github.cubecolony.Warps;

import com.google.common.collect.Lists;
import io.github.cubecolony.Warps.utils.SchedulerUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class WarpsManager {
    private List<Warp> warps = Lists.newArrayList();

    public WarpsManager() {
        SchedulerUtils.runDatabase((connection -> {
            warps = DatabaseManager.getWarps(connection);
        }));
    }

    public void addWarp(Warp warp) {
        for(Warp w : warps) {
            if (w.getUuid().equals(warp.getUuid())) removeWarp(w);
        }

        warps.add(warp);
        SchedulerUtils.runDatabaseAsync((connection -> {
            DatabaseManager.addWarp(connection, warp);
        }));
    }

    public void removeWarp(Warp warp) {
        for (int x = 0; x < warps.size(); x++) {
            if (warps.get(x).getUuid().equals(warp.getUuid())) warps.remove(x);
        }
        SchedulerUtils.runDatabaseAsync((connection -> {
            DatabaseManager.removeWarp(connection, warp.getUuid());
        }));
    }

    public boolean travelToWarp(Warp warp, Player player) {
        Location warpLocation = warp.getLocation();
        if (warpLocation == null || player == null) return false;

        player.teleport(warpLocation);
        return true;
    }

    public List<Warp> getWarps() {
        return warps;
    }
}
