package com.robertx22.age_of_exile.dimension.dungeon_data;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class DungeonAffixData {

    @Store
    public String affix = "";
    @Store
    public int perc = 100;

}
