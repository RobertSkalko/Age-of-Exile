package com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;

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
