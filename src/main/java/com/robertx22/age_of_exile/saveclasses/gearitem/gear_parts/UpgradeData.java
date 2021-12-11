package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.currency.GearBlessingType;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

@Storable
public class UpgradeData {

    @Store
    public GearBlessingType bless = GearBlessingType.NONE;

    public enum SlotType {
        EMPTY(true, 0, TextFormatting.DARK_GRAY),
        UP1(false, 1, TextFormatting.GREEN),
        UP2(false, 2, TextFormatting.BLUE),
        UP3(false, 3, TextFormatting.LIGHT_PURPLE);

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

        for (int i = 0; i < rarity.upgrades; i++) {
            ups.add(SlotType.EMPTY);
        }
    }

    public int getHowManyTimesCanBeUpgraded() {
        return getTimesCanBeUpgradedInTotal() - getTimesUpgraded();
    }

    public int getTimesCanBeUpgradedInTotal() {
        return ups.size();
    }

    public int getUpgradeLevel() {
        int lvl = ups.stream()
            .mapToInt(x -> x.upgradeLevel)
            .sum();

        return MathHelper.clamp(lvl, 0, 100);
    }

    public int getTimesUpgraded() {
        return (int) ups.stream()
            .filter(x -> !x.isEmptySlot)
            .count();
    }

}
