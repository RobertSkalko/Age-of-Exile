package com.robertx22.forgotten_magic.teleport;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.math.BlockPos;

@Storable
public class TeleportData {

    @Store
    public BlockPos altar_pos;
    @Store
    public BlockPos last_recall_pos;
}
