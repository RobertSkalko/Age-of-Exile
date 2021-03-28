package com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades;

import com.robertx22.age_of_exile.player_skills.items.backpacks.AutoPickupType;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackInfo;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum BackpackUpgrade {

    AUTO_GEM_PICKUP {
        @Override
        public void upgrade(BackpackUpgradeItem item, BackpackInfo info) {
            info.autoPickups.add(AutoPickupType.GEM);
        }

        @Override
        public Item craftItem() {
            return Items.GOLD_INGOT;
        }
    },
    AUTO_RUNE_PICKUP {
        @Override
        public void upgrade(BackpackUpgradeItem item, BackpackInfo info) {
            info.autoPickups.add(AutoPickupType.RUNE);
        }

        @Override
        public Item craftItem() {
            return Items.IRON_INGOT;
        }
    },
    AUTO_CURRENCY_PICKUP {
        @Override
        public void upgrade(BackpackUpgradeItem item, BackpackInfo info) {
            info.autoPickups.add(AutoPickupType.CURRENCY);
        }

        @Override
        public Item craftItem() {
            return Items.BOOK;
        }
    },
    Size {
        @Override
        public void upgrade(BackpackUpgradeItem item, BackpackInfo info) {
            info.tier = item.tier.tier;
            info.extraRows = item.tier.tier;
        }

        @Override
        public Item craftItem() {
            return Items.DIAMOND;
        }
    };

    public abstract void upgrade(BackpackUpgradeItem item, BackpackInfo info);

    public abstract Item craftItem();
}
