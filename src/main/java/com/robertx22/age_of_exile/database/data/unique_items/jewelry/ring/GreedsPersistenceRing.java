package com.robertx22.age_of_exile.database.data.unique_items.jewelry.ring;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.IncreasedItemQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.loot.MagicFind;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Health;
import com.robertx22.age_of_exile.database.data.unique_items.IUnique;
import com.robertx22.age_of_exile.database.registrators.BaseGearTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class GreedsPersistenceRing implements IUnique {

    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(-30, 25, IncreasedItemQuantity.getInstance(), ModType.FLAT),
            new StatModifier(-30, 25, MagicFind.getInstance(), ModType.FLAT),
            new StatModifier(5, 10, new ElementalResist(Elements.Elemental), ModType.FLAT),
            new StatModifier(-10, -5, Health.getInstance(), ModType.FLAT)

        );
    }

    @Override
    public String locDescForLangFile() {
        return "When desire for perfection overtakes your sanity, you too will be blessed.";
    }

    @Override
    public String locNameForLangFile() {
        return "Greed's Persistence";
    }

    @Override
    public String GUID() {
        return "greed_persist";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return BaseGearTypes.MID_TO_END_HP_RING_MANA_REG;
    }
}

