package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class Intelligence extends BaseCoreStat {

    private Intelligence() {
        this.statGroup = StatGroup.CORE;
    }

    public static final Intelligence INSTANCE = new Intelligence();
    public static String GUID = "intelligence";

    @Override
    public String locDescForLangFile() {
        return "Increase Magic Shield and Mana and Mana regen.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public List<StatModifier> statsThatBenefit() {
        return Arrays.asList(
            new StatModifier(1F, 1F, Mana.getInstance(), ModType.LOCAL_INCREASE),
            new StatModifier(0.5F, 0.5F, ManaRegen.getInstance(), ModType.LOCAL_INCREASE),
            new StatModifier(0.5F, 0.5F, MagicShield.getInstance(), ModType.GLOBAL_INCREASE)
        );
    }

    @Override
    public String locNameForLangFile() {
        return "Intelligence";
    }
}




