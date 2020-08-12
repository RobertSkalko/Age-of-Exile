package com.robertx22.age_of_exile.database.data.unique_items.armor;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.age_of_exile.database.data.stats.types.resources.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.unique_items.IUnique;
import com.robertx22.age_of_exile.database.registrators.BaseGearTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class InnerConfluxRobe implements IUnique {

    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(5, 15, MagicShield.getInstance(), ModType.FLAT),
            new StatModifier(10, 30, MagicShield.getInstance(), ModType.LOCAL_INCREASE),
            new StatModifier(1, 2, RegeneratePercentStat.MANA, ModType.FLAT),
            new StatModifier(1, 2, RegeneratePercentStat.MAGIC_SHIELD, ModType.FLAT),
            new StatModifier(-8, -3, Health.getInstance(), ModType.FLAT),
            new StatModifier(0.4F, 0.6F, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "Merge the Rivers of Mana and Magic, attain infinity, or die.";
    }

    @Override
    public String locNameForLangFile() {
        return "Inner Conflux";
    }

    @Override
    public String GUID() {
        return "inner_conflux";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return BaseGearTypes.END_CLOTH_HELMET;
    }
}
