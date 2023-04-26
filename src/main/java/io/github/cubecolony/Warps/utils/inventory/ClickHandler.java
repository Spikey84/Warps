package io.github.cubecolony.Warps.utils.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ClickHandler {
    boolean handle(Player player, ClickType type);

    static ClickHandler unclickable() {
        return (p, t) -> false;
    }

    static ClickHandler replace(BiConsumer<Player, ClickType> consumer) {
        return (player, type) -> {
            consumer.accept(player, type);
            return false;
        };
    }
}
