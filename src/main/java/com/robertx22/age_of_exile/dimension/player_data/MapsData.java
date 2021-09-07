package com.robertx22.age_of_exile.dimension.player_data;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.BlockPos;

@Storable
public class MapsData {

    @Store
    public DungeonData dungeonData = new DungeonData();

    @Store
    public BlockPos tel_pos = new BlockPos(0, 0, 0);
    @Store
    public String tp_b_dim = "";

    @Store
    public boolean isEmpty = true;

    @Store
    public String orig_gamemode = "";

}
