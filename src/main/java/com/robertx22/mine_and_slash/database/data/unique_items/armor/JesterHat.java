package com.robertx22.mine_and_slash.database.data.unique_items.armor;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.cloth.SorcererCirclet;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.mine_and_slash.database.data.stats.types.loot.IncreasedItemQuantity;
import com.robertx22.mine_and_slash.database.data.stats.types.loot.MagicFind;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalHit;
import com.robertx22.mine_and_slash.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class JesterHat implements IUnique {

    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(-15, 15, CriticalHit.getInstance(), ModType.FLAT),
            new StatModifier(-15, 15, CriticalDamage.getInstance(), ModType.FLAT),
            new StatModifier(-15, 15, MagicFind.getInstance(), ModType.FLAT),
            new StatModifier(-15, 15, IncreasedItemQuantity.getInstance(), ModType.FLAT),
            new StatModifier(0.5F, 1, new FlatIncreasedReq(Intelligence.INSTANCE), ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "One, two, three, fail. Whoops! One, two..";
    }

    @Override
    public String locNameForLangFile() {
        return "Jester's Intuition";
    }

    @Override
    public String GUID() {
        return "jester_hat";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return SorcererCirclet.INSTANCE;
    }
}
