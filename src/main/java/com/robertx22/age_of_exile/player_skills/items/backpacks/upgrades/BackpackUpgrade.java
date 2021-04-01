package com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.player_skills.items.backpacks.AutoPickupType;
import com.robertx22.age_of_exile.player_skills.items.backpacks.BackpackInfo;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.List;

public enum BackpackUpgrade {

    SAVE_FROM_SALVAGE {
        @Override
        public void upgrade(BackpackUpgradeItem item, BackpackInfo info) {
            info.dontSalvageEnchantedOrNamed = true;
        }

        @Override
        public Item craftItem() {
            return Items.NAME_TAG;
        }

        @Override
        public void addToTooltip(BackpackUpgradeItem backpackUpgradeItem, List<Text> list) {
            list.add(Words.SavesNamedOrEnchanted.locName());
        }
    },
    AUTO_SALVAGE_0 {
        @Override
        public void upgrade(BackpackUpgradeItem item, BackpackInfo info) {
            info.tiersToAutoSalvage.add(0);
        }

        @Override
        public Item craftItem() {
            return Items.FURNACE;
        }

        @Override
        public void addToTooltip(BackpackUpgradeItem backpackUpgradeItem, List<Text> list) {
            addSalvage(list, 0);
        }
    },
    AUTO_SALVAGE_1 {
        @Override
        public void upgrade(BackpackUpgradeItem item, BackpackInfo info) {
            info.tiersToAutoSalvage.add(1);
        }

        @Override
        public Item craftItem() {
            return Items.FURNACE;
        }

        @Override
        public void addToTooltip(BackpackUpgradeItem backpackUpgradeItem, List<Text> list) {
            addSalvage(list, 1);
        }
    },
    AUTO_SALVAGE_2 {
        @Override
        public void upgrade(BackpackUpgradeItem item, BackpackInfo info) {
            info.tiersToAutoSalvage.add(2);
        }

        @Override
        public Item craftItem() {
            return Items.FURNACE;
        }

        @Override
        public void addToTooltip(BackpackUpgradeItem backpackUpgradeItem, List<Text> list) {
            addSalvage(list, 2);
        }
    },
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
    AUTO_ESSENCE_PICKUP {
        @Override
        public void upgrade(BackpackUpgradeItem item, BackpackInfo info) {
            info.autoPickups.add(AutoPickupType.ESSENCE);
        }

        @Override
        public Item craftItem() {
            return Items.LAPIS_LAZULI;
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
            info.extraRows = item.tier.tier + 1;
        }

        @Override
        public Item craftItem() {
            return Items.DIAMOND;
        }

        @Override
        public boolean canOverrideSelf() {
            return true;
        }

        @Override
        public void addToTooltip(BackpackUpgradeItem bag, List<Text> list) {
            list.add(new LiteralText("Adds " + (bag.tier.tier + 1) * 9 + " Slots"));
        }
    };

    public boolean canOverrideSelf() {
        return false;
    }

    public void addToTooltip(BackpackUpgradeItem bag, List<Text> list) {

    }

    public void addSalvage(List<Text> list, int tier) {
        LiteralText txt = new LiteralText("Salvages: ");
        Database.GearRarities()
            .getFiltered(x -> x.item_tier == tier)
            .forEach(x -> {
                txt.append(x.locName()
                    .append(" "));
            });
        list.add(txt);
    }

    public abstract void upgrade(BackpackUpgradeItem item, BackpackInfo info);

    public abstract Item craftItem();
}
