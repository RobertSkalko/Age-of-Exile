package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;

@Storable
public class StatPointsData {

    @Store
    public HashMap<String, Integer> map = new HashMap<>();

    public void reset() {
        this.map.clear();
    }

    public void addStats(EntityCap.UnitData data) {
        map.entrySet()
            .forEach(x -> {
                float val = x.getValue();
                ExactStatData stat = ExactStatData.of(val, val, ExileDB.Stats()
                    .get(x.getKey()), ModType.FLAT, 1);
                stat.applyStats(data);
            });

    }

}
