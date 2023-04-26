package io.github.cubecolony.Warps.inventory;

import com.google.common.collect.Lists;
import io.github.cubecolony.Warps.Warp;
import io.github.cubecolony.Warps.WarpsManager;
import io.github.cubecolony.Warps.utils.inventory.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.TippedArrow;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class WarpsInventory extends BaseInventory {
    private static final int[][] FORMAT = {
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 2, 2, 2, 2, 2, 2, 2, 3 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
    };
    private static final ItemStack FILLER_ITEM;
    private static final ItemStack NEXT_PAGE_ITEM;
    private static final ItemStack PREVIOUS_PAGE_ITEM;
    static {
        FILLER_ITEM = ItemBuilder.of(Material.WHITE_STAINED_GLASS_PANE)
                .displayName(Component.text(""))
                .build();
        NEXT_PAGE_ITEM = ItemBuilder.of(Material.TIPPED_ARROW)
                .displayName(Component.text("Next page").style(Style.style(NamedTextColor.GREEN)
                        .decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false)))
                .setLore()
                .build();
        NEXT_PAGE_ITEM.editMeta(PotionMeta.class, meta -> meta.setBasePotionData(new PotionData(PotionType.LUCK)));
        PREVIOUS_PAGE_ITEM = ItemBuilder.of(Material.TIPPED_ARROW)
                .displayName(Component.text("Previous page").style(Style.style(NamedTextColor.RED)
                        .decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false)))
                .setLore()
                .build();
        PREVIOUS_PAGE_ITEM.editMeta(PotionMeta.class, meta -> meta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL)));
    }

    public WarpsInventory(String title, Player player, WarpsManager w, int page) {
        super(3, Component.text(title));
        int maxPage = (int) Math.ceil(w.getWarps().size() / 9.0f);
        List<Warp> warps = Lists.newArrayList(w.getWarps().subList((page-1)*7, Math.min(w.getWarps().size(), page*7)));
        compileFormat(replacement -> switch (replacement) {
            case 0 -> new ClickableItem(FILLER_ITEM, ClickHandler.unclickable());
            case 1 -> {
                if (page == 1) {
                    yield new ClickableItem(FILLER_ITEM, ClickHandler.unclickable());
                } else {
                    yield new ClickableItem(PREVIOUS_PAGE_ITEM, ClickHandler.replace((p, type) ->
                            new WarpsInventory(title, player, w, page-1).open(p)));
                }
            }
            case 3 -> {
                if (page == maxPage) {
                    yield new ClickableItem(FILLER_ITEM, ClickHandler.unclickable());
                } else {
                    yield new ClickableItem(NEXT_PAGE_ITEM, ClickHandler.replace((p, type) ->
                            new WarpsInventory(title, player, w, page+1).open(p)));
                }
            }
            case 2 -> {
                if (warps.isEmpty())
                    yield new ClickableItem(ItemBuilder.of(Material.AIR).build(), ClickHandler.unclickable());
                Warp warp = warps.remove(0);
                ItemStack item = ItemBuilder.of(Material.NAME_TAG)
                        .displayName(Component.text("Warp: ")
                                .style(Style.style(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD))
                                .decoration(TextDecoration.ITALIC, false)
                                .append(Component.text(warp.getName()).style(Style.style(NamedTextColor.AQUA))
                                        .decoration(TextDecoration.ITALIC, false)))
                        .setLore(Component.text("* Click to use the warp!").style(Style.style(NamedTextColor.GRAY)
                                .decoration(TextDecoration.ITALIC, false)))
                        .build();
                yield new ClickableItem(item, ClickHandler.replace((p, type) -> {
                    p.teleport(warp.getLocation());
                    p.sendMessage(Component.text("Teleported to warp " + warp.getName() + "!")
                            .style(Style.style(NamedTextColor.YELLOW)));
                    p.closeInventory();
                }));
            }
            default -> new ClickableItem(ItemBuilder.of(Material.AIR).build(), ClickHandler.unclickable());
        });
    }

    private void compileFormat(Function<Integer, ClickableItem> provider) {
        for (int r = 0; r < FORMAT.length; r++) {
            int[] row = FORMAT[r];
            for (int c = 0; c < row.length; c++) {
                int slot = r * 9 + c;
                int value = row[c];
                ClickableItem item = provider.apply(value);
                if (item != null)
                    addItem(slot, item.getItemStack(), item.getHandler());
            }
        }
    }

//    private void createInventory(Player player) {
//        fillInventory(I.getVisibleFiller());
//
//
//        if (warpsManager.getWarps().size() < 10) {
//            for (int x = 0; x < 9; x++) {
//                int slot = x+9;
//                int finalX = x;
//                final boolean exists = !(warpsManager.getWarps().size() <= x);
//                if (exists) {
//                    addItem(slot, I.setName(I.getConfirm(), warpsManager.getWarps().get(finalX).getName()), (clickType) -> {
//                        getWarpsManager().travelToWarp(getWarpsManager().getWarps().get(finalX), player);
//                        player.closeInventory();
//                    });
//                } else {
//                    addItem(slot, I.getFiller(), (clickType) -> {
//                        player.closeInventory();
//                    });
//                }
//            }
//        } else if (page == 1) {
//            for (int x = 0; x < 8; x++) {
//                int finalX = x;
//                int slot = x+9;
//                final boolean exists = !(warpsManager.getWarps().size() <= x);
//                if (exists) {
//                    addItem(slot, I.setName(I.getConfirm(), warpsManager.getWarps().get(finalX).getName()), (clickType) -> {
//                        getWarpsManager().travelToWarp(getWarpsManager().getWarps().get(finalX), player);
//                        player.closeInventory();
//                    });
//                } else {
//                    addItem(slot, I.getFiller(), (clickType) -> {
//                        player.closeInventory();
//                    });
//                }
//            }
//            addItem(8+9, I.getNext(), (clickType -> {
//                page = page + 1;
//                createInventory(player);
//            }));
//        } else {
//            for (int x = 0; x < 7; x++) {
//                int finalX = x;
//                int slot = x + 1+9;
//                int warp = page == 2 ? x + 8 : x + 8 + (7*(page-1));
//                final boolean exists = !(warpsManager.getWarps().size() <= warp);
//                if (exists && !(warpsManager.getWarps().get(warp) == null)) {
//                    addItem(slot, I.setName(I.getConfirm(), warpsManager.getWarps().get(warp).getName()), (clickType) -> {
//                        getWarpsManager().travelToWarp(getWarpsManager().getWarps().get(warp), player);
//                        player.closeInventory();
//                    });
//                } else {
//                    addItem(slot, I.getFiller(), (clickType) -> {
//                        player.closeInventory();
//                    });
//                }
//            }
//            addItem(0+9, I.getBack(), (clickType -> {
//                page = page - 1;
//                createInventory(player);
//            }));
//            int totalSlots = 0;
//            if (page == 2) {
//                totalSlots = 15;
//            } else {
//                totalSlots = 15 + (7 * (page-2));
//            }
//
//            if (totalSlots < warpsManager.getWarps().size()) {
//                addItem(8+9, I.getNext(), (clickType -> {
//                    page = page + 1;
//                    createInventory(player);
//                }));
//            } else {
//                addItem(8+9, I.getFiller());
//            }
//        }
//    }
}
