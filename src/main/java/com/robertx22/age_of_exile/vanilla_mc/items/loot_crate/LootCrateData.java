package com.robertx22.age_of_exile.vanilla_mc.items.loot_crate;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;

@Storable
public class LootCrateData {

    @Store
    public LootType type = LootType.Gem;
    @Store
    public int tier = 1;

    public enum Type {
        GEAR_SOUL,
    }

    public ItemStack createStack() {
        ItemStack stack = new ItemStack(ModRegistry.MISC_ITEMS.LOOT_CRATE);
        StackSaving.LOOT_CRATE.saveTo(stack, this);
        return stack;
    }

}
