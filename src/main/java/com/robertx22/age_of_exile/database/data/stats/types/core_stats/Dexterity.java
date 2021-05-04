package com.robertx22.age_of_exile.database.data.stats.types.core_stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.util.Formatting;

import java.util.Arrays;

public class Dexterity extends BaseCoreStat {

    public static final String GUID = "dexterity";

    public static final Dexterity INSTANCE = new Dexterity();

    private Dexterity() {
        super(Arrays.asList(
            new OptScaleExactStat(0.25F, 0.25F, Stats.STYLE_DAMAGE.get(PlayStyle.ranged), ModType.FLAT),
            new OptScaleExactStat(2, 2, DodgeRating.getInstance(), ModType.LOCAL_INCREASE)
        ));
        this.format = Formatting.GREEN;
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
