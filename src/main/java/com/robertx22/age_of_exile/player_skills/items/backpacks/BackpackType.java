package com.robertx22.age_of_exile.player_skills.items.backpacks;

import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.player_skills.items.alchemy.CondensedSalvageEssence;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.SalvagedDustItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public enum BackpackType {

    VALUABLES {
        @Override
        public String getName() {
            return "Valuables";
        }

        @Override
        public Item getCraftItem() {
            return Items.GOLD_INGOT;
        }

        @Override
        public boolean canAcceptStack(ItemStack stack) {
            Item item = stack.getItem();
            return item instanceof GemItem
                || item instanceof RuneItem
                || item instanceof CurrencyItem
                || item instanceof SalvagedDustItem
                || item instanceof CondensedSalvageEssence
                ;
        }

        @Override
        public boolean autoPicksUp() {
            return true;
        }
    },
    NORMAL {
        @Override
        public String getName() {
            return "";
        }

        @Override
        public Item getCraftItem() {
            return Items.IRON_INGOT;
        }

        @Override
        public boolean canAcceptStack(ItemStack stack) {
            return true;
        }

        @Override
        public boolean autoPicksUp() {
            return false;
        }
    },
    GATHERING_MATS {
        @Override
        public String getName() {
            return "Material";
        }

        @Override
        public Item getCraftItem() {
            return Items.WHEAT_SEEDS;
        }

        @Override
        public boolean canAcceptStack(ItemStack stack) {
            Item item = stack.getItem();
            return item instanceof IGatheringMat;
        }

        @Override
        public boolean autoPicksUp() {
            return true;
        }
    };

    public abstract String getName();

    public abstract Item getCraftItem();

    public abstract boolean canAcceptStack(ItemStack stack);

    public abstract boolean autoPicksUp();

}
