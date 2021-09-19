package com.robertx22.age_of_exile.dimension;

import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.phys.AABB;

public class SpawnUtil {

    public static boolean canPlaceMob(World world, EntityType type, BlockPos p) {
        AABB box = type.getAABB(p.getX(), p.getY(), p.getZ());
        boolean can = world.noCollision(box);
        return can;
    }

    public static boolean canPlaceBlock(World world, BlockPos p) {
        if (!world.isEmptyBlock(p.above()) || !world.isEmptyBlock(p) || !world.getBlockState(p.below())
            .isRedstoneConductor(world, p.below())) {

            return false;
        }
        return true;
    }
}
