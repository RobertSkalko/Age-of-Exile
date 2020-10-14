package com.robertx22.age_of_exile.mobs.ai;

import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class GriefPathNodeMaker extends LandPathNodeMaker {

    @Override
    protected PathNodeType adjustNodeType(BlockView world, boolean canOpenDoors, boolean canEnterOpenDoors, BlockPos pos, PathNodeType type) {

        if (type == PathNodeType.BLOCKED) {
            if (!world.getBlockState(pos)
                .isAir()) {
                return PathNodeType.DOOR_OPEN;
            }
        }

        return super.adjustNodeType(world, canOpenDoors, canEnterOpenDoors, pos, type);
    }
}
