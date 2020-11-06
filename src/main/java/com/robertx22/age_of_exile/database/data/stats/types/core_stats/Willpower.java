package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
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
            new StatModifier(0.75F, 0.75F, MagicShield.getInstance(), ModType.FLAT),
            new StatModifier(0.1F, 0.1F, MagicShieldRegen.getInstance(), ModType.FLAT)
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
