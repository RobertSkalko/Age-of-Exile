package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;

public class PotionStat {

    ModType type = ModType.FLAT;
    float value;
    Stat stat;

    public PotionStat(float value, Stat stat) {
        this.value = value;
        this.stat = stat;
    }

    public ExactStatData getExactStat(EntityCap.UnitData data, PlayerSpellCap.ISpellsCap cap, ExtraPotionData pdata, BasePotionEffect effect) {

        float multi = 1;

        float finalVal = pdata.getStacks() * value * multi;

        ExactStatData statData = ExactStatData.scaleTo(finalVal, type, stat.GUID(), data.getLevel());

        return statData;

    }

}
