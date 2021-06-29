package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.tiers.base.Difficulty;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class DungeonAffixes {

    @Store
    public List<String> s = new ArrayList<>();
    @Store
    public List<String> p = new ArrayList<>();

    public List<ExactStatData> getStats(int lvl) {
        List<ExactStatData> list = new ArrayList<>();

        getAffixes().forEach(x -> {
            list.addAll(x.getTierStats((Integer) x.tier_map.keySet()
                    .toArray()[0])
                .stream()
                .map(s -> s.ToExactStat(100, lvl))
                .collect(Collectors.toList()));
        });

        return list;
    }

    public List<Affix> getAffixes() {
        List<Affix> list = new ArrayList<>();
        s.forEach(x -> {
            list.add(ExileDB.Affixes()
                .get(x));
        });
        p.forEach(x -> {
            list.add(ExileDB.Affixes()
                .get(x));
        });
        return list;

    }

    public void randomize(Difficulty diff) {

    }

}
