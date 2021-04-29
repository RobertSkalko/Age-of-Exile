package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;

@Storable
public class DungeonAffixes {

    @Store
    public DungeonAffixData suffix = new DungeonAffixData();
    @Store
    public DungeonAffixData prefix = new DungeonAffixData();

    public List<ExactStatData> getStats(int floor, int lvl) {
        List<ExactStatData> list = new ArrayList<>();
        list.addAll(suffix.getStats(lvl));
        list.addAll(prefix.getStats(lvl));

        float num = 5 + (5 * floor);
        list.add(ExactStatData.of(num, num, ExtraMobDropsStat.getInstance(), ModType.FLAT, 1));
        return list;
    }

    public void randomize(int tier) {

        suffix.affix = Database.Affixes()
            .getFilterWrapped(x -> x.type == Affix.Type.dungeon_suffix)
            .random()
            .GUID();
        prefix.affix = Database.Affixes()
            .getFilterWrapped(x -> x.type == Affix.Type.dungeon_prefix)
            .random()
            .GUID();
    }

}
