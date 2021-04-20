package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.dimension.DimensionIds;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Arrays;

public class WorldUtils {

    public static void spawnEntity(World world, Entity entity) {

        world.spawnEntity(entity);

    }

    public static boolean isNearSurface(BlockPos pos, World world, int buffer) {

        BlockPos surface = WorldUtils.getSurface(world, pos);

        if (pos.getY() > surface.getY() - buffer) {
            return true;
        }

        return false;
    }

    public static BlockPos getSurfaceCenterOfChunk(WorldAccess world, BlockPos pos) {

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

    public static boolean surfaceIsWater(WorldAccess world, BlockPos pos) {

        BlockPos surface = getSurface(world, pos);

        for (BlockPos x : Arrays.asList(surface.up(), surface.up(2), surface.down(), surface.down(2), surface)) {
            if (world.getBlockState(x)
                .getMaterial() == Material.WATER) {
                return true;
            }
        }

        return false;

    }

    public static BlockPos getSurface(WorldAccess world, BlockPos pos) {

        pos = new BlockPos(pos.getX(), world.getSeaLevel(), pos.getZ());

        boolean goingDown = world.isAir(pos);

        while (world.isAir(pos) || world.getBlockState(pos)
            .getBlock() instanceof LeavesBlock) {

            if (goingDown) {
                pos = pos.down();
            } else {
                pos = pos.up();
            }

        }

        while (world.isAir(pos.up()) == false) {
            pos = pos.up();
        }

        return pos.up();

    }

    public static boolean isDungeonWorld(WorldAccess world) {

        if (world == null) {
            return false;
        }
        return isMapWorldClass(world);
    }

    public static boolean isMapWorldClass(WorldAccess world) {
        return world.getRegistryManager()
            .getDimensionTypes()
            .getId(world.getDimension())
            .equals(DimensionIds.DUNGEON_DIMENSION);
    }

    public static int getTier(World world, BlockPos pos) {

        return Database.getDimensionConfig(world).mob_tier;

    }

}
