package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.List;
import java.util.stream.Collectors;

@Storable
public class DungeonAffixData {

    @Store
    public String affix = "";
    @Store
    public int perc = 100;

    public Affix getAffix() {
        return Database.Affixes()
            .get(affix);

    }

    public List<ExactStatData> getStats(int lvl) {
        return getAffix().getTierStats((Integer) getAffix().tier_map.keySet()
            .toArray()[0])
            .stream()
            .map(x -> x.ToExactStat(perc, lvl))
            .collect(Collectors.toList());
    }

}
