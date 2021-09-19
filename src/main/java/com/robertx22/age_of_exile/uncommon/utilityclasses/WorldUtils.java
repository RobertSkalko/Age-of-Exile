package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.dimension.DimensionIds;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.level.block.LeavesBlock;

import java.util.Arrays;

public class WorldUtils {

    public static void spawnEntity(World world, Entity entity) {

        world.addFreshEntity(entity);

    }

    public static boolean isNearSurface(BlockPos pos, World world, int buffer) {

        BlockPos surface = WorldUtils.getSurface(world, pos);

        if (pos.getY() > surface.getY() - buffer) {
            return true;
        }

        return false;
    }

    public static BlockPos getSurfaceCenterOfChunk(IWorld world, BlockPos pos) {

        int x = world.getChunk(pos)
            .getPos().x + 8;
        int z = world.getChunk(pos)
            .getPos().z + 8;

        pos = furtherby8(pos);

        pos = getSurface(world, pos);

        return pos;
    }

    public static BlockPos furtherby8(BlockPos pos) {

        int x = 0;
        int z = 0;

        if (pos.getX() > 0) {
            x = pos.getX() + 8;
        } else {
            x = pos.getX() - 8;
        }

        if (pos.getZ() > 0) {
            z = pos.getZ() + 8;
        } else {
            z = pos.getZ() - 8;
        }

        pos = new BlockPos(x, pos.getY(), z);

        return pos;

    }

    public static boolean surfaceIsWater(IWorld world, BlockPos pos) {

        BlockPos surface = getSurface(world, pos);

        for (BlockPos x : Arrays.asList(surface.above(), surface.above(2), surface.below(), surface.below(2), surface)) {
            if (world.getBlockState(x)
                .getMaterial() == Material.WATER) {
                return true;
            }
        }

        return false;

    }

    public static BlockPos getSurface(IWorld world, BlockPos pos) {

        pos = new BlockPos(pos.getX(), world.getSeaLevel(), pos.getZ());

        boolean goingDown = world.isEmptyBlock(pos);

        while (world.isEmptyBlock(pos) || world.getBlockState(pos)
            .getBlock() instanceof LeavesBlock) {

            if (goingDown) {
                pos = pos.below();
            } else {
                pos = pos.above();
            }

        }

        while (world.isEmptyBlock(pos.above()) == false) {
            pos = pos.above();
        }

        return pos.above();

    }

    public static boolean isDungeonWorld(IWorldReader world) {
        return isId(world, DimensionIds.DUNGEON_DIMENSION);
    }

    public static boolean isMapWorldClass(IWorldReader world) {
        return isId(world, DimensionIds.DUNGEON_DIMENSION);

    }

    static boolean isId(IWorldReader world, ResourceLocation dimid) {

        if (MMORPG.server == null) {
            return false;
        }
        ResourceLocation id = MMORPG.server.registryAccess()
            .dimensionTypes()
            .getKey(world.dimensionType());

        if (id != null) {
            return id.equals(dimid);
        }

        return false;
    }

}
