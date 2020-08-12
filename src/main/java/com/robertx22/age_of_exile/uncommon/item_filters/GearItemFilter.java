package com.robertx22.age_of_exile.uncommon.item_filters;

import com.robertx22.age_of_exile.uncommon.item_filters.bases.ItemFilter;
import com.robertx22.age_of_exile.uncommon.datasaving.Gear;
import net.minecraft.item.ItemStack;

public class GearItemFilter extends ItemFilter {

    @Override
    public boolean IsValidItem(ItemStack stack) {
        return Gear.has(stack);
    }
}
