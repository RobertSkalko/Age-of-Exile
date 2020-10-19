package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class Agility extends BaseCoreStat {

    public static final String GUID = "agility";

    public static final Agility INSTANCE = new Agility();

    private Agility() {
        super(Arrays.asList(
            new StatModifier(0.2F, 0.2F, Health.getInstance(), ModType.FLAT), // todo until better idea
            new StatModifier(1F, 1F, DodgeRating.getInstance(), ModType.FLAT)
        ));
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public String locNameForLangFile() {
        return "Agility";
    }
}
