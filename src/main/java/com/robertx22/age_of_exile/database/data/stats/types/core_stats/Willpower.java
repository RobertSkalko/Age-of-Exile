package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class Willpower extends BaseCoreStat {

    public static final String GUID = "willpower";
    public static final Willpower INSTANCE = new Willpower();

    private Willpower() {
        super(Arrays.asList(
            new OptScaleExactStat(20, 20, MagicShield.getInstance(), ModType.FLAT),
            new OptScaleExactStat(1, 1, MagicShieldRegen.getInstance(), ModType.FLAT)
        ));
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public String locNameForLangFile() {
        return "Willpower";
    }
}
