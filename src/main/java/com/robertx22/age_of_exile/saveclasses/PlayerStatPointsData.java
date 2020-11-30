package com.robertx22.age_of_exile.saveclasses;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;

@Storable
public class PlayerStatPointsData implements IApplyableStats {

    @Store
    public HashMap<String, Integer> stats = new HashMap<>();

    public void addPoint(Stat stat) {
        if (stat instanceof BaseCoreStat) {
            stats.put(stat.GUID(), stats.getOrDefault(stat.GUID(), 0) + 1);
        } else {
            System.out.print("Trying to allocate a stat that isn't a core stat: " + stat.GUID());
        }
    }

    public int getTotal() {
        if (stats.isEmpty()) {
            return 0;
        }
        return stats.entrySet()
            .stream()
            .filter(x -> Database.Stats()
                .isRegistered(x.getKey()))
            .map(e -> e.getValue())
            .reduce((a, b) -> a + b)
            .get();
    }

    @Override
    public void applyStats(EntityCap.UnitData data) {
        stats.entrySet()
            .forEach(x -> data.getUnit()
                .getStatInCalculation(Database.Stats()
                    .get(x.getKey()))
                .addAlreadyScaledFlat(x.getValue()));

    }
}
