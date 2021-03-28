package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import net.minecraft.item.ItemStack;

public enum AutoPickupType {

    GEM {
        @Override
        public boolean autoPicksUp(ItemStack stack) {
            return stack.getItem() instanceof GemItem;
        }
    },
    RUNE {
        @Override
        public boolean autoPicksUp(ItemStack stack) {
            return stack.getItem() instanceof RuneItem;
        }
    },
    CURRENCY {
        @Override
        public boolean autoPicksUp(ItemStack stack) {
            return stack.getItem() instanceof CurrencyItem;
        }
    };

    public abstract boolean autoPicksUp(ItemStack stack);
}
