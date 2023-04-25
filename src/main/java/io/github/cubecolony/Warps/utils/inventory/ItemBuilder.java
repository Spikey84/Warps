package io.github.cubecolony.Warps.utils.inventory;

import com.google.common.collect.Lists;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public class ItemBuilder {
    private final ItemStack itemStack;
    private final ItemMeta meta;

    private ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.meta = itemStack.getItemMeta();
    }

    public static ItemBuilder of(Material material, int amount) {
        return new ItemBuilder(new ItemStack(material, amount));
    }

    public static ItemBuilder of(Material material) {
        return of(material, 1);
    }

    public ItemBuilder updateMeta(Consumer<ItemMeta> modifier) {
        modifier.accept(meta);
        return this;
    }

    public ItemBuilder setLore(Component... lines) {
        return updateMeta(meta -> meta.lore(Lists.newArrayList(lines)));
    }

    public <T extends ItemMeta> ItemBuilder updateMeta(Class<T> type, Consumer<T> modifier) {
        if (meta.getClass().isAssignableFrom(type)) {
            T t = type.cast(meta);
            modifier.accept(t);
        }
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder displayName(Component component) {
        return updateMeta(meta -> meta.displayName(component));
    }

    public ItemStack build() {
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
