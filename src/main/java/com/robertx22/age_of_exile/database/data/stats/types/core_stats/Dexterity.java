package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackStyleDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class Dexterity extends BaseCoreStat {

    public static final String GUID = "dexterity";

    public static final Dexterity INSTANCE = new Dexterity();

    private Dexterity() {
        super(Arrays.asList(
            new StatModifier(0.5F, 0.5F, CriticalHit.getInstance(), ModType.FLAT),
            new StatModifier(1, 1, AttackStyleDamage.RANGED, ModType.FLAT)
        ));
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public String locNameForLangFile() {
        return "Dexterity";
    }
}
