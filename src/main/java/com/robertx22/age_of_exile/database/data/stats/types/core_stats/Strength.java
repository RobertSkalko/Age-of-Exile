package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class Strength extends BaseCoreStat {

    public static final String GUID = "strength";
    public static final Strength INSTANCE = new Strength();

    private Strength() {
        this.statGroup = StatGroup.CORE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases Physical Dmg and health";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public List<StatModifier> statsThatBenefit() {
        return Arrays.asList(
            new StatModifier(0.25F, 0.25F, new AttackDamage(Elements.Physical), ModType.LOCAL_INCREASE),
            new StatModifier(1, 1, CriticalDamage.getInstance(), ModType.FLAT)
        );
    }

    @Override
    public String locNameForLangFile() {
        return "Strength";
    }
}
