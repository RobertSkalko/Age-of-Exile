package com.robertx22.age_of_exile.vanilla_mc.items.crates.gem_crate;

import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.ItemStack;

@Storable
public class LootCrateData {

    @Store
    public LootType type = LootType.Currency;
    @Store
    public int tier = 1;

    public enum Type {
        GEAR_SOUL,
    }

    public ItemStack createStack() {
        ItemStack stack = new ItemStack(SlashItems.LOOT_CRATE.get());
        stack.getOrCreateTag()
            .putInt("CustomModelData", type.custommodeldata);

        StackSaving.GEM_CRATE.saveTo(stack, this);
        return stack;
    }

}
