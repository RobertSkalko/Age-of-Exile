package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

@Storable
public class UpgradeData {

    public enum SlotType {
        // n is one, m is minus
        M1(false, -1, TextFormatting.RED),
        M2(false, -2, TextFormatting.RED),
        M3(false, -3, TextFormatting.RED),
        Zero(false, 0, TextFormatting.DARK_GRAY),
        N1(false, 1, TextFormatting.GREEN),
        N2(false, 2, TextFormatting.BLUE),
        N3(false, 3, TextFormatting.LIGHT_PURPLE),
        Empty(true, 0, TextFormatting.GRAY),
        Gold(true, 0, TextFormatting.GOLD);

        public boolean isEmptySlot;
        public int upgradeLevel;
        public TextFormatting format;

        SlotType(boolean isEmptySlot, int upgradeLevel, TextFormatting format) {
            this.isEmptySlot = isEmptySlot;
            this.upgradeLevel = upgradeLevel;
            this.format = format;
        }
    }

    @Store
    public List<SlotType> ups = new ArrayList<>();    // upgrades

    public void regenerate(GearRarity rarity) {

        this.ups.clear();

        int upgrades = rarity.upgrades.random();

        for (int i = 0; i < upgrades; i++) {
            if (RandomUtils.roll(10)) {
                ups.add(UpgradeData.SlotType.Gold);
            } else {
                ups.add(UpgradeData.SlotType.Empty);
            }
        }
    }

    public int getHowManyTimesCanBeUpgraded() {
        return getTimesCanBeUpgradedInTotal() - getTimesUpgraded();
    }

    public boolean isNextSlotGold() {
        return ups.stream()
            .filter(x -> x.isEmptySlot)
            .findFirst()
            .get() == SlotType.Gold;
    }

    public int getTimesCanBeUpgradedInTotal() {
        return ups.size();
    }

    public int getUpgradeLevel() {
        return ups.stream()
            .mapToInt(x -> x.upgradeLevel)
            .sum();
    }

    public int getTimesUpgraded() {
        return (int) ups.stream()
            .filter(x -> !x.isEmptySlot)
            .count();
    }

}
