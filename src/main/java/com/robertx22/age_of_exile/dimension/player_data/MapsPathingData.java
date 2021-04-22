package com.robertx22.age_of_exile.dimension.player_data;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.HashSet;
import java.util.Set;

@Storable
public class MapsPathingData {

    @Store
    public int floor = 0;

    @Store
    public Set<PathData> entered = new HashSet<>();

    public boolean enteredAnotherDungeonOnSameFloor(DungeonData dungeon, int floor) {
        return entered.stream()
            .anyMatch(x -> x.floor == floor && !x.uuid.equals(dungeon.uuid));

    }

}
