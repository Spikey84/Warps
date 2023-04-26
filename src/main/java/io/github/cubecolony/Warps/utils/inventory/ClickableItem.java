package io.github.cubecolony.Warps.utils.inventory;

import org.bukkit.inventory.ItemStack;

public class ClickableItem {
    private final ItemStack itemStack;
    private final ClickHandler handler;

    public ClickableItem(ItemStack itemStack, ClickHandler handler) {
        this.itemStack = itemStack;
        this.handler = handler;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ClickHandler getHandler() {
        return handler;
    }
}
