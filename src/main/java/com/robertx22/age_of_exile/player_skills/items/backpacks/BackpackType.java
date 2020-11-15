package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum BackpackType {

    VALUABLES {
        @Override
        public boolean canAcceptStack(ItemStack stack) {
            Item item = stack.getItem();
            return item instanceof GemItem || item instanceof RuneItem || item instanceof CurrencyItem;
        }
    },
    GATHERING_MATS {
        @Override
        public boolean canAcceptStack(ItemStack stack) {
            Item item = stack.getItem();
            return item instanceof IGatheringMat;
        }
    };

    public abstract boolean canAcceptStack(ItemStack stack);

}
