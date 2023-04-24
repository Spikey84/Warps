package io.github.cubecolony.Warps.utils.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class BaseInventory implements InventoryHolder, Listener {
    private Inventory inventory;
    private HashMap<Integer, Consumer<ClickType>> clickableItems;
    private boolean isOpen = false;

    public BaseInventory(int rows, Plugin plugin, String title) {
        clickableItems = Maps.newHashMap();
        this.inventory = Bukkit.createInventory(this, rows*9, StringUtils.formatColors(title));
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

   public void addItem(int slot, ItemStack item) {
       clickableItems.remove(slot);
       inventory.setItem(slot, item);
   }

   public void addItem(int slot, ItemStack item, Consumer<ClickType> runnable) {
        clickableItems.put(slot, runnable);
        inventory.setItem(slot, item);
   }

   public void fillInventory(ItemStack item) {
        for (int x = 0; x < inventory.getSize(); x++) {
            if (clickableItems.containsKey(x)) clickableItems.remove(x);
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

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (!event.getClickedInventory().equals(inventory)) {
            if (event.getClickedInventory().equals(event.getWhoClicked().getInventory()) && isOpen) {
                event.setCancelled(true);
            }
            return;
        }


        event.setCancelled(true);

        for (Map.Entry<Integer, Consumer<ClickType>> entry : Lists.newArrayList(clickableItems.entrySet())) {
            if (event.getSlot() != entry.getKey()) continue;
            if (event.getClick().equals(ClickType.LEFT)) entry.getValue().accept(ClickType.LEFT);
            if (event.getClick().equals(ClickType.RIGHT)) entry.getValue().accept(ClickType.RIGHT);
            if (event.getClick().equals(ClickType.SHIFT_LEFT)) entry.getValue().accept(ClickType.SHIFT_LEFT);
            if (event.getClick().equals(ClickType.SHIFT_RIGHT)) entry.getValue().accept(ClickType.SHIFT_RIGHT);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!event.getInventory().equals(inventory)) return;

        isOpen = false;
    }

}
