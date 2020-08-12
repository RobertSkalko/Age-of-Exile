package com.robertx22.age_of_exile.uncommon.item_filters;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.uncommon.item_filters.bases.ItemFilter;
import net.minecraft.item.ItemStack;

public class CurrencyItemFilter extends ItemFilter {

    @Override
    public boolean IsValidItem(ItemStack stack) {

        return stack.getItem() instanceof CurrencyItem;
    }
}

