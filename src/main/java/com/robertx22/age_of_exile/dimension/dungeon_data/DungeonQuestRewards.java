package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.dimension.item.DungeonKeyItem;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Storable
public class DungeonQuestRewards {

    @Store
    public List<ItemStack> stacks = new ArrayList<>();

    public void randomize(DungeonKeyItem dunkey, int tier, boolean isEndOfMap) {

        stacks.clear();

        int tiercalc = tier;

        if (!isEndOfMap) {
            tiercalc /= 10;
        }

        if (isEndOfMap) {
            tiercalc += tier / 15;
            ItemStack key = new ItemStack(dunkey);
            stacks.add(key);
        }
        if (tiercalc < 1) {
            tiercalc = 1;
        }

        if (tiercalc >= ModRegistry.CURRENCIES.BIG_TIER.increaseTierBy()) {
            ItemStack stack = new ItemStack(ModRegistry.CURRENCIES.BIG_TIER);
            int amount = tier / ModRegistry.CURRENCIES.BIG_TIER.increaseTierBy();
            stack.setCount(amount);
            tiercalc -= amount * ModRegistry.CURRENCIES.BIG_TIER.increaseTierBy();
            stacks.add(stack);
        }
        if (tiercalc >= ModRegistry.CURRENCIES.MED_TIER.increaseTierBy()) {
            ItemStack stack = new ItemStack(ModRegistry.CURRENCIES.MED_TIER);
            int amount = tier / ModRegistry.CURRENCIES.MED_TIER.increaseTierBy();
            stack.setCount(amount);
            tiercalc -= amount * ModRegistry.CURRENCIES.MED_TIER.increaseTierBy();
            stacks.add(stack);
        }
        if (tiercalc >= ModRegistry.CURRENCIES.SMALL_TIER.increaseTierBy()) {
            ItemStack stack = new ItemStack(ModRegistry.CURRENCIES.SMALL_TIER);
            int amount = tier / ModRegistry.CURRENCIES.SMALL_TIER.increaseTierBy();
            stack.setCount(amount);
            tiercalc -= amount * ModRegistry.CURRENCIES.SMALL_TIER.increaseTierBy();
            stacks.add(stack);
        }

    }
}
