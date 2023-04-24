package io.github.cubecolony.Warps.inventory;

import io.github.cubecolony.Warps.WarpsManager;
import io.github.cubecolony.Warps.utils.inventory.BaseInventory;
import io.github.cubecolony.Warps.utils.inventory.I;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class WarpsInventory extends BaseInventory {
    private WarpsManager warpsManager;
    private int page = 1;

    public WarpsInventory(Plugin plugin, String title, Player player, WarpsManager w) {
        super(3, plugin, title);
        warpsManager = w;

        createInventory(player);
    }

    private void createInventory(Player player) {
        fillInventory(I.getVisibleFiller());


        if (warpsManager.getWarps().size() < 10) {
            for (int x = 0; x < 9; x++) {
                int slot = x+9;
                int finalX = x;
                final boolean exists = !(warpsManager.getWarps().size() <= x);
                if (exists) {
                    addItem(slot, I.setName(I.getConfirm(), warpsManager.getWarps().get(finalX).getName()), (clickType) -> {
                        getWarpsManager().travelToWarp(getWarpsManager().getWarps().get(finalX), player);
                        player.closeInventory();
                    });
                } else {
                    addItem(slot, I.getFiller(), (clickType) -> {
                        player.closeInventory();
                    });
                }
            }
        } else if (page == 1) {
            for (int x = 0; x < 8; x++) {
                int finalX = x;
                int slot = x+9;
                final boolean exists = !(warpsManager.getWarps().size() <= x);
                if (exists) {
                    addItem(slot, I.setName(I.getConfirm(), warpsManager.getWarps().get(finalX).getName()), (clickType) -> {
                        getWarpsManager().travelToWarp(getWarpsManager().getWarps().get(finalX), player);
                        player.closeInventory();
                    });
                } else {
                    addItem(slot, I.getFiller(), (clickType) -> {
                        player.closeInventory();
                    });
                }
            }
            addItem(8+9, I.getNext(), (clickType -> {
                page = page + 1;
                createInventory(player);
            }));
        } else {
            for (int x = 0; x < 7; x++) {
                int finalX = x;
                int slot = x + 1+9;
                int warp = page == 2 ? x + 8 : x + 8 + (7*(page-1));
                final boolean exists = !(warpsManager.getWarps().size() <= warp);
                if (exists && !(warpsManager.getWarps().get(warp) == null)) {
                    addItem(slot, I.setName(I.getConfirm(), warpsManager.getWarps().get(warp).getName()), (clickType) -> {
                        getWarpsManager().travelToWarp(getWarpsManager().getWarps().get(warp), player);
                        player.closeInventory();
                    });
                } else {
                    addItem(slot, I.getFiller(), (clickType) -> {
                        player.closeInventory();
                    });
                }
            }
            addItem(0+9, I.getBack(), (clickType -> {
                page = page - 1;
                createInventory(player);
            }));
            int totalSlots = 0;
            if (page == 2) {
                totalSlots = 15;
            } else {
                totalSlots = 15 + (7 * (page-2));
            }

            if (totalSlots < warpsManager.getWarps().size()) {
                addItem(8+9, I.getNext(), (clickType -> {
                    page = page + 1;
                    createInventory(player);
                }));
            } else {
                addItem(8+9, I.getFiller());
            }
        }
    }

    public WarpsManager getWarpsManager() {
        return warpsManager;
    }
}
