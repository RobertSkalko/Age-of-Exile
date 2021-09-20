package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.armor_materials;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;

public enum ArmorTier {
    ZERO(0, ArmorMaterial.IRON, LevelRanges.STARTER),
    ONE(1, ArmorMaterial.IRON, LevelRanges.LOW),
    TWO(2, ArmorMaterial.DIAMOND, LevelRanges.MIDDLE),
    THREE(3, ArmorMaterial.DIAMOND, LevelRanges.HIGH),
    FOUR(4, ArmorMaterial.NETHERITE, LevelRanges.ENDGAME);

    int tier;
    IArmorMaterial vanillaMat;
    LevelRange lvl;

    ArmorTier(int tier, IArmorMaterial vanillaMat, LevelRange lvl) {
        this.tier = tier;
        this.vanillaMat = vanillaMat;
        this.lvl = lvl;
    }

    public static ArmorTier from(LevelRange range) {

        for (ArmorTier tier : ArmorTier.values()) {
            if (tier.lvl == range) {
                return tier;
            }
        }
        return null;

    }
}
