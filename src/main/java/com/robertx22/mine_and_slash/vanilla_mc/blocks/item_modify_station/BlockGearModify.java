package com.robertx22.mine_and_slash.vanilla_mc.blocks.item_modify_station;

import com.robertx22.mine_and_slash.vanilla_mc.blocks.bases.BaseInventoryBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class BlockGearModify extends BaseInventoryBlock {

    public BlockGearModify() {
        super(Settings.of(Material.STONE).strength(5F));
    }

    @Override
    public BlockEntity createTileEntity(BlockState state, BlockView world) {

        return new TileGearModify();

    }

}