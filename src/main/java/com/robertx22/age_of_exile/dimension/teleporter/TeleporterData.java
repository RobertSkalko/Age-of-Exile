package com.robertx22.age_of_exile.dimension.teleporter;

import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonData;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonType;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class TeleporterData {

    @Store
    public DungeonType dungeon_type = DungeonType.DUNGEON;
    @Store
    public boolean activated_rift = false;
    @Store
    public DungeonData rift_data = new DungeonData();
    @Store
    public int life_ticks = 20 * 60 * 10;

    public void tick(TeleportedBlockEntity be) {
        if (dungeon_type.isRift() && !activated_rift) {
            life_ticks--;

            if (life_ticks < 0) {
                be.getWorld()
                    .breakBlock(be.getPos(), false);

            }
        }
    }

}
