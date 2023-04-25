package io.github.cubecolony.Warps.utils.inventory;

import com.google.common.collect.Maps;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class BaseInventory implements InventoryHolder, Listener {
    private final Inventory inventory;
    HashMap<Integer, ClickHandler> clickableItems;
    boolean isOpen = false;

    public BaseInventory(int rows, Component title) {
        clickableItems = Maps.newHashMap();
        inventory = Bukkit.createInventory(this, rows*9, title);
    }

   public void addItem(int slot, ItemStack item) {
       clickableItems.remove(slot);
       inventory.setItem(slot, item);
   }

   public void addItem(int slot, ItemStack item, ClickHandler handler) {
        clickableItems.put(slot, handler);
        inventory.setItem(slot, item);
   }

   public void fillInventory(ItemStack item) {
        for (int x = 0; x < inventory.getSize(); x++) {
            // if (clickableItems.containsKey(x)) - unnecessary check
            clickableItems.remove(x);
            inventory.setItem(x, item);
        }
   }

   public void open(Player player) {
        player.openInventory(inventory);
        isOpen = true;
   }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    void handleClose(Player player) {
    }
}
