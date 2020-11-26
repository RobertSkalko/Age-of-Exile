package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class Strength extends BaseCoreStat {

    public static final String GUID = "strength";
    public static final Strength INSTANCE = new Strength();

    private Strength() {
        super(Arrays.asList(
            new OptScaleExactStat(5, 5, Health.getInstance(), ModType.FLAT),
            new OptScaleExactStat(2, 2, Armor.getInstance(), ModType.LOCAL_INCREASE)
        ));
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public String locNameForLangFile() {
        return "Strength";
    }
}
