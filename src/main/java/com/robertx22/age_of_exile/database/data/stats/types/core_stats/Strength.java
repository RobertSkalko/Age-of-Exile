package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
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
            new StatModifier(0.15F, 0.15F, new AttackDamage(Elements.Physical), ModType.GLOBAL_INCREASE),
            new StatModifier(0.75F, 0.75F, Health.getInstance(), ModType.GLOBAL_INCREASE)
        );
    }

    @Override
    public String locNameForLangFile() {
        return "Strength";
    }
}
