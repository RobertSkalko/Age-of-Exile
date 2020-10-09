package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class Dexterity extends BaseCoreStat {

    public static final String GUID = "dexterity";

    public static final Dexterity INSTANCE = new Dexterity();

    private Dexterity() {
        this.statGroup = StatGroup.CORE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases Crit Hit and Dodge";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public List<StatModifier> statsThatBenefit() {
        return Arrays.asList(
            new StatModifier(1F, 1F, DodgeRating.getInstance(), ModType.GLOBAL_INCREASE),
            new StatModifier(0.2F, 0.2F, CriticalHit.getInstance(), ModType.GLOBAL_INCREASE)
        );
    }

    @Override
    public String locNameForLangFile() {
        return "Dexterity";
    }
}
