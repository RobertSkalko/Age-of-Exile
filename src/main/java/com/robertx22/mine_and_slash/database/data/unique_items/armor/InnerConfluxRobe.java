package com.robertx22.mine_and_slash.database.data.unique_items.armor;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.OccultistRobes;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.mine_and_slash.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class InnerConfluxRobe implements IUnique {

    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(5, 15, MagicShield.getInstance(), ModType.FLAT),
            new StatModifier(10, 30, MagicShield.getInstance(), ModType.LOCAL_INCREASE),
            new StatModifier(1, 2, RegeneratePercentStat.MANA, ModType.FLAT),
            new StatModifier(1, 2, RegeneratePercentStat.MAGIC_SHIELD, ModType.FLAT),
            new StatModifier(-8, -3, Health.getInstance(), ModType.FLAT),
            new StatModifier(0.5F, 1, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "Merge the Rivers of Mana and Magic, attain infinity, or die.";
    }

    @Override
    public String locNameForLangFile() {
        return "Inner Conflux";
    }

    @Override
    public String GUID() {
        return "inner_conflux";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return OccultistRobes.INSTANCE;
    }
}
