package com.robertx22.mine_and_slash.database.data.unique_items.weapons.wand;

import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Strength;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.misc.HealToSpellDmgStat;
import com.robertx22.mine_and_slash.database.data.stats.types.reduced_req.FlatIncreasedReq;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.HealPower;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.database.registrators.BaseGearTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.Arrays;
import java.util.List;

public class DivineMightScepter implements IUnique {

    @Override
    public List<StatModifier> uniqueStats() {
        return Arrays.asList(
            new StatModifier(1, 3, 3, 5, new WeaponDamage(Elements.Thunder), ModType.FLAT),
            new StatModifier(5, 25, HealPower.getInstance(), ModType.FLAT),
            new StatModifier(1, 1, HealToSpellDmgStat.getInstance(), ModType.FLAT),
            new StatModifier(0.25F, 0.5F, new FlatIncreasedReq(Strength.INSTANCE), ModType.FLAT)
        );
    }

    @Override
    public String locDescForLangFile() {
        return "God might prefer peaceful means, but sometimes peace is not an option.";
    }

    @Override
    public String locNameForLangFile() {
        return "Divine Might";
    }

    @Override
    public String GUID() {
        return "divine_might";
    }

    @Override
    public BaseGearType getBaseGearType() {
        return BaseGearTypes.END_WAND;
    }

    @Override
    public int Weight() {
        return 500;
    }

}
