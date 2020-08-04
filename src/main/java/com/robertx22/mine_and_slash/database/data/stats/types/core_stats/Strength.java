package com.robertx22.mine_and_slash.database.data.stats.types.core_stats;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Health;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.List;

public class Strength extends BaseCoreStat {

    public static final String GUID = "strength";
    public static final Strength INSTANCE = new Strength();

    private Strength() {

    }

    @Override
    public Formatting getIconFormat() {
        return Formatting.RED;
    }

    @Override
    public String getIconPath() {
        return "core/str";
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
            new StatModifier(0.15F, 0.15F, new WeaponDamage(Elements.Physical), ModType.GLOBAL_INCREASE),
            new StatModifier(0.75F, 0.75F, Health.getInstance(), ModType.GLOBAL_INCREASE)
        );
    }

    @Override
    public String locNameForLangFile() {
        return "Strength";
    }
}
