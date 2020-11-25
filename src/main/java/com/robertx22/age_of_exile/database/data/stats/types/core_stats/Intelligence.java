package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalDamageBonus;
import com.robertx22.age_of_exile.database.data.stats.types.offense.AttackStyleDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class Intelligence extends BaseCoreStat {

    private Intelligence() {
        super(Arrays.asList(
            new OptScaleExactStat(0.5F, 0.5F, new ElementalDamageBonus(Elements.Elemental), ModType.FLAT),
            new OptScaleExactStat(0.5F, 0.5F, AttackStyleDamage.MAGIC, ModType.FLAT),
            new OptScaleExactStat(1, 1, MagicShield.getInstance(), ModType.LOCAL_INCREASE)
        ));
        this.statGroup = StatGroup.CORE;
    }

    public static final Intelligence INSTANCE = new Intelligence();
    public static String GUID = "intelligence";

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public String locNameForLangFile() {
        return "Intelligence";
    }
}




