package com.robertx22.age_of_exile.dimension.teleporter;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonType;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.BlockPos;

@Storable
public class PortalBlocKData {

    @Store
    public BlockPos dungeonPos = new BlockPos(0, 0, 0);
    @Store
    public BlockPos tpbackpos = new BlockPos(0, 80, 0);
    @Store
    public DungeonType dungeonType = DungeonType.DUNGEON;

    public PortalBlocKData(BlockPos dungeonPos, BlockPos tpbackpos, DungeonType dungeonType) {
        this.dungeonPos = dungeonPos;
        this.tpbackpos = tpbackpos;
        this.dungeonType = dungeonType;
    }

    public PortalBlocKData() {
    }
}
