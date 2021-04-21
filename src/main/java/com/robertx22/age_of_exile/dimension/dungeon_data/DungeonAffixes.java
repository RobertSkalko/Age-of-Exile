package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.Database;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class DungeonAffixes {
    @Store
    public DungeonAffixData suffix = new DungeonAffixData();
    @Store
    public DungeonAffixData prefix = new DungeonAffixData();

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
