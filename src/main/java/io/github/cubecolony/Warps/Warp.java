package io.github.cubecolony.Warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class Warp {
    private UUID uuid;
    private String name;
    private String serverName;
    private UUID worldUUID;
    private long x;
    private long y;
    private long z;
    private int pitch;
    private int yaw;

    public Warp(UUID uuid, String name, String serverName, UUID worldUUID, long x, long y, long z, int pitch, int yaw) {
        this.uuid = uuid;
        this.name = name;
        this.serverName = serverName;
        this.worldUUID = worldUUID;
        this.x = x;

        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(worldUUID), x, y, z, pitch, yaw);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getServerName() {
        return serverName;
    }

    public UUID getWorldUUID() {
        return worldUUID;
    }

    public int getPitch() {
        return pitch;
    }

    public int getYaw() {
        return yaw;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }

    public String getName() {
        return name;
    }
}


