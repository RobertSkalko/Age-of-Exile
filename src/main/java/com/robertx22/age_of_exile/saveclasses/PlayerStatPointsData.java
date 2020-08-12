package com.robertx22.age_of_exile.saveclasses;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Intelligence;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Strength;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class PlayerStatPointsData implements IApplyableStats {

    @Store
    public int dexterity = 0;
    @Store
    public int intelligence = 0;
    @Store
    public int strength = 0;

    public void addPoint(Stat stat) {

        if (stat == Dexterity.INSTANCE) {
            dexterity++;
        } else if (stat == Intelligence.INSTANCE) {
            intelligence++;
        } else if (stat == Strength.INSTANCE) {
            strength++;
        } else {
            System.out.println("Error, trying to allocate a stat not a core stat!");
        }
    }

    public int getTotal() {
        return dexterity + intelligence + strength;
    }

    @Override
    public void applyStats(EntityCap.UnitData data) {
        data.getUnit()
            .getCreateStat(Dexterity.INSTANCE)
            .addAlreadyScaledFlat(dexterity);
        data.getUnit()
            .getCreateStat(Intelligence.INSTANCE)
            .addAlreadyScaledFlat(intelligence);
        data.getUnit()
            .getCreateStat(Strength.INSTANCE)
            .addAlreadyScaledFlat(strength);

    }
}
