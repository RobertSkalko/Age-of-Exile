package com.robertx22.mine_and_slash.database.data.unique_items.jewelry.necklace;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.curios.OccultNecklace;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShieldRegen;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class SkullOfSpiritsNecklace implements IUnique {
    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(0.5F, 1F, AllAttributes.getInstance(), ModType.FLAT),
            new StatModifier(10, 20, MagicShieldRegen.getInstance(), ModType.LOCAL_INCREASE),
            new StatModifier(-5, -15, new ElementalResist(Elements.Water), ModType.FLAT),
            new StatModifier(-5, -15, new ElementalResist(Elements.Fire), ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "The mysterious skull contains knowledge of the contained spirits, but also their madness.";
    }

    @Override
    public String locNameForLangFile() {
        return "Skull of Spirits";
    }

    @Override
    public String GUID() {
        return "skull_of_spirits";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return OccultNecklace.INSTANCE;
    }
}
