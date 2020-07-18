package com.robertx22.mine_and_slash.database.data.spells.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.EntityContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public abstract class BaseSpellBlock extends Block {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);

    public BaseSpellBlock() {
        super(Block.Settings.of(Material.UNDERWATER_PLANT, MaterialColor.RED)
                      .noCollision()
                      .strength(5)
                      .sounds(BlockSoundGroup.WET_GRASS));

    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView reader, BlockPos pos, EntityContext ctx) {
        return SHAPE;
    }

}
