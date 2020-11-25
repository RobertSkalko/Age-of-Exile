package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.offense.Accuracy;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackStyleDamage;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class Dexterity extends BaseCoreStat {

    public static final String GUID = "dexterity";

    public static final Dexterity INSTANCE = new Dexterity();

    private Dexterity() {
        super(Arrays.asList(
            new OptScaleExactStat(20, 20, Accuracy.getInstance(), ModType.FLAT),
            new OptScaleExactStat(0.25F, 0.25F, AttackStyleDamage.RANGED, ModType.FLAT),
            new OptScaleExactStat(1, 1, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
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
