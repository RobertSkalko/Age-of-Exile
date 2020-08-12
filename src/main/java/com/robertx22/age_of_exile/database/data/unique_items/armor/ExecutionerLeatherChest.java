package com.robertx22.age_of_exile.database.data.unique_items.armor;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.age_of_exile.database.data.unique_items.IUnique;
import com.robertx22.age_of_exile.database.registrators.BaseGearTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class ExecutionerLeatherChest implements IUnique {

    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(5, 15, DodgeRating.getInstance(), ModType.FLAT),
            new StatModifier(10, 40, DodgeRating.getInstance(), ModType.LOCAL_INCREASE),
            new StatModifier(5, 20, CriticalHit.getInstance(), ModType.FLAT),
            new StatModifier(0.25F, 0.5F, new FlatIncreasedReq(Strength.INSTANCE), ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "To miss is not an option.";
    }

    @Override
    public String locNameForLangFile() {
        return "Executioner's Pride";
    }

    @Override
    public String GUID() {
        return "exec_pride";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return BaseGearTypes.END_LEATHER_CHEST;
    }
}
