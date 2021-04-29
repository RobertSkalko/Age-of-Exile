package com.robertx22.age_of_exile.dimension.teleporter;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashMap;
import java.util.List;

@Storable
public class MapsData {

    @Store
    public HashMap<Integer, List<DungeonData>> dungeonsByFloors = new HashMap<>();

    @Store
    public boolean isEmpty = true;

    @Store
    public String orig_gamemode = "";
}
