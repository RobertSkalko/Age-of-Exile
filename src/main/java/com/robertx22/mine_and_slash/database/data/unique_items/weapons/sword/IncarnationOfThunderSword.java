package com.robertx22.mine_and_slash.database.data.unique_items.weapons.sword;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.weapons.melee.GemstoneSword;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ChanceToApplyEffect;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.reduced_req.ReducedAllStatReqOnItem;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class IncarnationOfThunderSword implements IUnique {

    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(1, 1, 10, 16, new WeaponDamage(Elements.Thunder), ModType.FLAT),
            new StatModifier(5, 15, ChanceToApplyEffect.STATIC, ModType.FLAT),
            new StatModifier(-20, 20, CriticalDamage.getInstance(), ModType.FLAT),
            new StatModifier(-200, -10, new ReducedAllStatReqOnItem(), ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "The ability to wield thunder is said to come once in a millennium.";
    }

    @Override
    public String locNameForLangFile() {
        return "Incarnation of Thunder";
    }

    @Override
    public String GUID() {
        return "inca_thunder";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return GemstoneSword.INSTANCE;
    }
}

