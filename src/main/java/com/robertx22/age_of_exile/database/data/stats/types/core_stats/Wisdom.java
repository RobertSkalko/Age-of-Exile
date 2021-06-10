package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.Formatting;

import java.util.Arrays;

public class Wisdom extends BaseCoreStat {

    public static final String GUID = "wisdom";
    public static final Wisdom INSTANCE = new Wisdom();

    private Wisdom() {
        super(Arrays.asList(
            new OptScaleExactStat(10, 10, Mana.getInstance(), ModType.FLAT),
            new OptScaleExactStat(0.5F, 0.5F, ManaRegen.getInstance(), ModType.FLAT)
        ));
        this.format = Formatting.LIGHT_PURPLE.getName();
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

