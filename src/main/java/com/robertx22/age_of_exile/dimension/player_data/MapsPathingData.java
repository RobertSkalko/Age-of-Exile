package com.robertx22.age_of_exile.dimension.player_data;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.saveclasses.PointData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

@Storable
public class MapsPathingData {

    @Store
    public BlockPos tel_pos = new BlockPos(0, 0, 0);

    @Store
    public PointData point_pos = new PointData(25, 25);

    @Store
    public HashMap<PointData, DungeonData> dungeon_datas = new HashMap<>();

}
