package com.robertx22.mine_and_slash.database.data.stats.types.core_stats;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Mana;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.ManaRegen;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;

public class Intelligence extends BaseCoreStat {

    private Intelligence() {

    }

    public static final Intelligence INSTANCE = new Intelligence();
    public static String GUID = "intelligence";

    @Override
    public Formatting getIconFormat() {
        return Formatting.BLUE;
    }

    @Override
    public String getIconPath() {
        return "core/int";
    }

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
            new StatModifier(1F, 1F, MagicShield.getInstance(), ModType.GLOBAL_INCREASE)
        );
    }

    @Override
    public String locNameForLangFile() {
        return "Intelligence";
    }
}




