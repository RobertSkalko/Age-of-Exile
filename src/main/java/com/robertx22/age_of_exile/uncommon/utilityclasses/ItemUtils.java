package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ItemUtils {
    public static Item.Settings getDefaultGearProperties() {

        Item.Settings prop = new Item.Settings().group(CreativeTabs.MyModTab);

        return prop;
    }

    public static Item randomMagicEssence() {
        List<IWeighted> list = Arrays.asList((IWeighted) ModRegistry.MISC_ITEMS.MAGIC_ESSENCE, (IWeighted) ModRegistry.MISC_ITEMS.RARE_MAGIC_ESSENCE);
        return (Item) RandomUtils.weightedRandom(list);

    }

    public enum Action {
        SIMULATE, MERGE
    }

    public static boolean mergeItems(List<ItemStack> itemsToMove, List<ItemStack> places, Action action) {

        int moved = 0;

        boolean failed = false;

        while (moved < itemsToMove.size() && !itemsToMove.isEmpty() && !failed) {

            for (int i = 0; i < places.size(); i++) {
                ItemStack place = places.get(i);
                ItemStack move = itemsToMove.get(0);

                if (canMergeItems(move, place)) {
                    if (action == Action.MERGE) {
                        ItemStack merged = mergeItems(move, place);
                        places.set(i, merged);
                        itemsToMove.remove(0);
                    } else {
                        moved++;
                    }
                    break;
                } else {
                    failed = true;
                }
            }

        }

        return itemsToMove.size() == moved;
    }

    public static ItemStack mergeItems(ItemStack first, ItemStack second) {
        ItemStack result = second;
        if (second.isEmpty()) {
            result = first;
        } else {
            result.setCount(second.getCount() + first.getCount());
        }
        return result;
    }

    public static boolean canMergeItems(ItemStack first, ItemStack second) {
        if (second.isEmpty()) {
            return true;
        }
        if (first.getItem() != second.getItem()) {
            return false;
        } else if (first.getDamage() != second.getDamage()) {
            return false;
        } else if (first.getCount() + second.getCount() > first.getMaxCount()) {
            return false;
        } else {
            return ItemStack.areTagsEqual(first, second);
        }
    }

}
