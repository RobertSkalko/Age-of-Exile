package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import com.robertx22.library_of_exile.tile_bases.BaseTile;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;

public abstract class BaseModificationStation extends BaseTile {

    public BaseModificationStation(BlockEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public abstract boolean modifyItem(PlayerEntity player);

}