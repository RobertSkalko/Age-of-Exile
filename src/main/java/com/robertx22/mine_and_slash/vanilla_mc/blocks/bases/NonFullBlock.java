package com.robertx22.mine_and_slash.vanilla_mc.blocks.bases;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

public abstract class NonFullBlock extends Block {

    public static final DirectionProperty direction = HorizontalFacingBlock.FACING;
    public static final BooleanProperty light = RedstoneTorchBlock.LIT;

    public NonFullBlock(Settings properties) {
        super(properties.nonOpaque());

        this.setDefaultState(
            this.stateManager.getDefaultState()
                .with(direction, Direction.NORTH)
                .with(light, Boolean.valueOf(true)));

    }

    public boolean isNotSolid(BlockState state) {
        return true;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState()
            .with(direction, context.getPlayerFacing()
                .getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rot) {
        return state.with(direction, rot.rotate(state.get(direction)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.get(direction)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(direction, light);
    }

}
