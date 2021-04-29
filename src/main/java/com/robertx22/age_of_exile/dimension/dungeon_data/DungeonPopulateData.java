package com.robertx22.age_of_exile.dimension.dungeon_data;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class DungeonPopulateData {

    @Store
    public int mobs = 0;
    @Store
    public int chests = 0;

    @Store
    public int pieces = 0;
}
