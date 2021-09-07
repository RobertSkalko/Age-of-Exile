package com.robertx22.age_of_exile.dimension.teleporter;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonType;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class TeleporterData {

    @Store
    public DungeonType type = DungeonType.DUNGEON;
    @Store
    public boolean activated = false;
    @Store
    public DungeonData data = new DungeonData();

}
