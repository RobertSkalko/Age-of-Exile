package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.SalvagedDustItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;

public enum AutoPickupType {

    GEM {
        @Override
        public boolean autoPicksUp(ItemStack stack) {
            return stack.getItem() instanceof GemItem;
        }
    },
    TRASH {
        @Override
        public boolean autoPicksUp(ItemStack stack) {

            Item item = stack.getItem();
            // todo make static
            List<Item> TRASH_ITEMS = Arrays.asList(
                Items.ROTTEN_FLESH, Items.STRING, Items.SPIDER_EYE, Items.BOW, Items.BONE
            );

            return TRASH_ITEMS.stream()
                .anyMatch(x -> item == x);

        }
    },
    ESSENCE {
        @Override
        public boolean autoPicksUp(ItemStack stack) {
            return stack.getItem() instanceof SalvagedDustItem;
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
