package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.dimension.database.dungeon_mob_lists.DungeonMobList;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.MutableText;

@Storable
public class DungeonData {

    @Store
    public PossibleUniques uniques = new PossibleUniques();
    @Store
    public DungeonAffixes affixes = new DungeonAffixes();
    @Store
    public String mob_list = "";
    @Store
    public int lvl = 1;
    @Store
    public int tier = 1;

    public MutableText getAffixedName() {
        return affixes.prefix.getAffix()
            .locName()
            .append(" ")
            .append(" Lair ")
            .append(affixes.suffix.getAffix()
                .locName());
    }

    public DungeonMobList getMobList() {
        return Database.DungeonMobLists()
            .get(mob_list);
    }

}
