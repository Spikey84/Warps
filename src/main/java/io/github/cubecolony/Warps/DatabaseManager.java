package io.github.cubecolony.Warps;

import com.google.common.collect.Lists;
import io.github.cubecolony.Warps.utils.UUIDUtils;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.sql.*;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {
    private static File databaseFile;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void initDatabase(Plugin plugin) {

        File databaseFolder = new File(plugin.getDataFolder(), "core.db");
        if (!databaseFolder.exists()) {
            try {
                databaseFolder.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        databaseFile = databaseFolder;
    }

    public static void createTables(Connection connection) {
        Statement statement = null;
        try {
            Class.forName("org.sqlite.JDBC");

            statement = connection.createStatement();

            String query = """
                    CREATE TABLE IF NOT EXISTS warps (\
                      uuid VARCHAR(36) PRIMARY KEY,\
                      name VARCHAR(36) NOT NULL,\
                      server VARCHAR(36) NOT NULL,\
                      world VARCHAR(36) NOT NULL,\
                      x bigint NOT NULL,\
                      y bigint NOT NULL,\
                      z bigint NOT NULL,\
                      yaw int NOT NULL,\
                      pitch int NOT NULL\
                    );
                    """;
            statement.executeUpdate(query);
            statement.close();

            statement = null;
            statement = connection.createStatement();

            query = """
                    CREATE TABLE IF NOT EXISTS PWIndex (\
                      uuid VARCHAR(36) PRIMARY KEY,\
                      name VARCHAR(36) NOT NULL,\
                      playeruuid VARCHAR(36) NOT NULL,\
                      server VARCHAR(36) NOT NULL,\
                      world VARCHAR(36) NOT NULL,\
                      x bigint NOT NULL,\
                      y bigint NOT NULL,\
                      z bigint NOT NULL,\
                      yaw int NOT NULL,\
                      pitch int NOT NULL\
                    );
                    """;
            statement.executeUpdate(query);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Warp> getWarps(Connection connection) {
        Statement statement = null;
        List<Warp> warps = Lists.newArrayList();

        try {
            Class.forName("org.sqlite.JDBC");

            String query = "SELECT uuid, name, server, world, x, y, z, pitch, yaw FROM warps;";

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                UUID uuid = UUIDUtils.build(resultSet.getString("uuid"));
                String name = resultSet.getString("name");
                String serverName = resultSet.getString("server");
                UUID worldUUID = UUIDUtils.build(resultSet.getString("world"));
                long x = resultSet.getLong("x");
                long y = resultSet.getLong("y");
                long z = resultSet.getLong("z");
                int pitch = resultSet.getInt("pitch");
                int yaw = resultSet.getInt("yaw");
                warps.add(new Warp(uuid, name, serverName, worldUUID, x, y, z, pitch, yaw));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return warps;
    }

    public static void addWarp(Connection connection, Warp warp) {
        PreparedStatement statement = null;

        removeWarp(connection, warp.getUuid());

        try {
            Class.forName("org.sqlite.JDBC");

            String query = """
                    INSERT INTO warps (uuid, name, server, world, x, y, z, pitch, yaw)\
                    VALUES\
                    (?, ?, ?, ?, ?, ?, ?, ?, ?);\
                    """;
            statement = connection.prepareStatement(query);

            statement.setString(1, UUIDUtils.strip(warp.getUuid()));
            statement.setString(2, warp.getName());
            statement.setString(3, warp.getServerName());
            statement.setString(4, UUIDUtils.strip(warp.getWorldUUID()));
            statement.setLong(5, warp.getX());
            statement.setLong(6, warp.getY());
            statement.setLong(7, warp.getZ());
            statement.setInt(8, warp.getPitch());
            statement.setInt(9, warp.getYaw());

            statement.execute();

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeWarp(Connection connection, UUID uuid) {
        PreparedStatement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");

            String query = """
                    DELETE FROM warps WHERE uuid = ?;
                    """;
            statement = connection.prepareStatement(query);
            statement.setString(1, UUIDUtils.strip(uuid));
            statement.execute();

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
