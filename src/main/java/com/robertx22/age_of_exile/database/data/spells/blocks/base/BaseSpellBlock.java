package com.robertx22.age_of_exile.database.data.spells.blocks.base;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public abstract class BaseSpellBlock extends Block implements BlockEntityProvider {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);

    public BaseSpellBlock() {
        super(Block.Settings.of(Material.UNDERWATER_PLANT, MaterialColor.RED)
            .noCollision()
            .strength(5, 2));

    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView reader, BlockPos pos, ShapeContext ctx) {
        return SHAPE;
    }

}
