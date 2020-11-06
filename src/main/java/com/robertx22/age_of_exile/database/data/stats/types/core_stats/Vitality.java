package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class Vitality extends BaseCoreStat {

    public static final String GUID = "vitality";
    public static final Vitality INSTANCE = new Vitality();

    private Vitality() {
        super(Arrays.asList(
            new StatModifier(1.5F, 1.5F, Health.getInstance(), ModType.FLAT),
            new StatModifier(0.05F, 0.05F, HealthRegen.getInstance(), ModType.FLAT)
        ));
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public String locNameForLangFile() {
        return "Vitality";
    }
}
