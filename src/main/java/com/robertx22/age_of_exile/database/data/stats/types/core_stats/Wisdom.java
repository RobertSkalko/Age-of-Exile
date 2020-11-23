package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class Wisdom extends BaseCoreStat {

    public static final String GUID = "wisdom";

    public static final Wisdom INSTANCE = new Wisdom();

    private Wisdom() {
        super(Arrays.asList(
            new StatModifier(3F, 3F, Mana.getInstance(), ModType.FLAT),
            new StatModifier(0.1F, 0.1F, ManaRegen.getInstance(), ModType.FLAT)
        ));
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public String locNameForLangFile() {
        return "Wisdom";
    }
}
