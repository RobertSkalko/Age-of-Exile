package com.robertx22.age_of_exile.dimension.player_data;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class PathData {

    @Store
    public String uuid = "";

    @Store
    public int floor = 0;

    public PathData() {

    }

    public PathData(String uuid, int floor) {
        this.uuid = uuid;
        this.floor = floor;
    }
}
