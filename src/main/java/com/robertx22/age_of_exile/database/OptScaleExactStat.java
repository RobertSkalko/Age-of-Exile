package com.robertx22.age_of_exile.database;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class OptScaleExactStat implements IApplyableStats {

    public float first = 0;
    public float second = 0;
    public String stat;
    public String type;

    public boolean scaleToLevel = true;

    ModType getModType() {
        return ModType.fromString(type);
    }

    public StatModifier toStatModifier() {
        Stat stat = SlashRegistry.Stats()
            .get(this.stat);
        if (stat.UsesSecondValue()) {
            return new StatModifier(first, first, second, second, stat, getModType());
        } else {
            return new StatModifier(first, second, stat, getModType());
        }
    }

    @Override
    public void applyStats(EntityCap.UnitData data) {
        if (scaleToLevel) {
            toStatModifier().ToExactStat(100, data.getLevel())
                .applyStats(data);
        } else {
            toStatModifier().ToExactStat(100, 1)
                .applyStats(data);
        }
    }

}
