package com.robertx22.age_of_exile.dimension;

import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class SpawnUtil {

    public static boolean canPlaceMob(World world, BlockPos p) {
        Box box = EntityType.SPIDER.createSimpleBoundingBox(p.getX(), p.getY(), p.getZ());
        return world.isSpaceEmpty(box);
    }

    public static boolean canPlaceBlock(World world, BlockPos p) {
        if (!world.isAir(p.up()) || !world.isAir(p) || !world.getBlockState(p.down())
            .isSolidBlock(world, p.down())) {

            return false;
        }
        return true;
    }
}
