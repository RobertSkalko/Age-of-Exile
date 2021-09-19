package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public abstract class OpaqueBlock extends Block {

    public static final DirectionProperty direction = HorizontalBlock.FACING;
    public static final BooleanProperty light = RedstoneTorchBlock.LIT;

    public OpaqueBlock(Properties properties) {
        super(properties.noOcclusion());

        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(direction, Direction.NORTH)
                .setValue(light, false));

    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState()
            .setValue(direction, context.getHorizontalDirection()
                .getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(direction, rot.rotate(state.getValue(direction)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(direction)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(direction, light);
    }

}
