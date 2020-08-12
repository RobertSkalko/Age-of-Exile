package com.robertx22.age_of_exile.database.data.unique_items.jewelry.necklace;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.PlusResourceOnKill;
import com.robertx22.age_of_exile.database.data.unique_items.IUnique;
import com.robertx22.age_of_exile.database.registrators.BaseGearTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class BirthingMiracleNecklace implements IUnique {
    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(3, 8, Health.getInstance(), ModType.FLAT),
            new StatModifier(0.1F, 0.2F, AllAttributes.getInstance(), ModType.FLAT),
            new StatModifier(2, 5, PlusResourceOnKill.HEALTH, ModType.FLAT),
            new StatModifier(0.5F, 0.75F, new FlatIncreasedReq(Strength.INSTANCE), ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "Life can sometimes keep on giving.";
    }

    @Override
    public String locNameForLangFile() {
        return "Birthing Miracle";
    }

    @Override
    public String GUID() {
        return "birth_miracle";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return BaseGearTypes.MID_TO_END_HP_NECKLACE;
    }
}

