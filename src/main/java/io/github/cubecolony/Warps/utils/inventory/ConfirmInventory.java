package io.github.cubecolony.Warps.utils.inventory;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class ConfirmInventory extends BaseInventory {
    public ConfirmInventory(Plugin plugin, String title, Player player, Consumer<Boolean> consumer) {
        super(1, plugin, title);

        fillInventory(I.getFiller());

        addItem(2, I.getDeny(), (clickType) -> {
            player.closeInventory();
            consumer.accept(false);


        });

        addItem(6, I.getConfirm(), (clickType) -> {
            player.closeInventory();
            consumer.accept(true);

        });
    }
}
