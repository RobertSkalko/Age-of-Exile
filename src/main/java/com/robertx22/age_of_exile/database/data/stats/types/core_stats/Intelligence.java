package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.DataStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.Formatting;

import java.util.Arrays;

public class Intelligence extends BaseCoreStat {

    private Intelligence() {
        super(Arrays.asList(
            new OptScaleExactStat(0.2F, 0.2F, SpellDamage.getInstance(), ModType.FLAT),
            new OptScaleExactStat(0.25F, 0.25F, DataStats.SPELL_CRIT_CHANCE.get(), ModType.FLAT)
        ));
        this.format = Formatting.BLUE;
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




