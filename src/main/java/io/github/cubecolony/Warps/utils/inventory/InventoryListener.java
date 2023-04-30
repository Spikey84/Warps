package io.github.cubecolony.Warps.utils.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory1 = event.getClickedInventory();
        if (inventory1 == null) return;
        if (event.getWhoClicked() instanceof Player player
                && inventory1.getHolder() instanceof BaseInventory inventory) {
            int slot = event.getSlot();
            ClickHandler handler = inventory.clickableItems.get(slot);
            if (handler != null) {
                boolean handle = handler.handle(player, event.getClick());
                event.setCancelled(!handle);
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        if (event.getInitiator().getHolder() instanceof BaseInventory)
            event.setCancelled(true);
    }

    @EventHandler
    public void onInventory2(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof BaseInventory) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory() instanceof BaseInventory inventory) {
            inventory.handleClose((Player) event.getPlayer());
        }
    }
}
