package com.robertx22.mine_and_slash.database.data.stats.types.core_stats;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalHit;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import java.util.Arrays;
import java.util.List;
import net.minecraft.util.Formatting;

public class Dexterity extends BaseCoreStat {

    public static final String GUID = "dexterity";

    public static final Dexterity INSTANCE = new Dexterity();

    private Dexterity() {

    }

    @Override
    public Formatting getIconFormat() {
        return Formatting.GREEN;
    }

    @Override
    public String getIconPath() {
        return "core/dex";
    }

    @Override
    public String locDescForLangFile() {
        return "Increases Crit Hit and Dodge";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public List<StatModifier> statsThatBenefit() {
        return Arrays.asList(
            new StatModifier(1F, 1F, DodgeRating.getInstance(), ModType.GLOBAL_INCREASE),
            new StatModifier(0.2F, 0.2F, CriticalHit.getInstance(), ModType.GLOBAL_INCREASE)
        );
    }

    @Override
    public String locNameForLangFile() {
        return "Dexterity";
    }
}
