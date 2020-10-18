package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class Willpower extends BaseCoreStat {

    public static final String GUID = "willpower";
    public static final Willpower INSTANCE = new Willpower();

    private Willpower() {
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
            new StatModifier(0.5F, 0.5F, MagicShield.getInstance(), ModType.FLAT),
            new StatModifier(0.2F, 0.2F, MagicShieldRegen.getInstance(), ModType.FLAT)
        );
    }

    @Override
    public String locNameForLangFile() {
        return "Willpower";
    }
}
