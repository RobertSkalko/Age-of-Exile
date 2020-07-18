package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.database.data.DimensionConfig;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class LevelUtils {

    public static int getExpRequiredForLevel(int level) {
        return level * level * 20;
    }

    // dirty hack, let's see how useful you are.
    public static float getExpDropForLevel(int level) {
        return getExpRequiredForLevel((int) MathHelper.clamp(level * 0.8F, 1, Integer.MAX_VALUE)) / 50F;
    }

    public static int determineLevel(World world, BlockPos pos, PlayerEntity nearestPlayer) {

        DimensionConfig dimConfig = SlashRegistry.getDimensionConfig(world);

        int lvl = 0;

        if (dimConfig.scale_to_nearest_player) {
            if (nearestPlayer != null) {
                lvl = Load.Unit(nearestPlayer)
                    .getLevel();
            } else {
                lvl = determineLevelPerDistanceFromSpawn(world, pos, dimConfig);
            }
        } else {
            lvl = determineLevelPerDistanceFromSpawn(world, pos, dimConfig);
        }

        lvl = MathHelper.clamp(dimConfig.min_lvl + lvl, dimConfig.min_lvl, dimConfig.max_lvl);

        return lvl;
    }

    public static int determineLevelPerDistanceFromSpawn(World world, BlockPos pos, DimensionConfig config) {

        BlockPos spawnPos = world.getSpawnPos();

        double distance = world.getSpawnPos()
            .getManhattanDistance(pos);

        int lvl = 1;

        lvl = (int) (1 + (distance / config.mob_lvl_per_distance));

        return MathHelper.clamp(lvl, config.min_lvl, config.max_lvl);

    }

    public static BlockPos getAreaPosOfLevel(World world, int level, DimensionConfig config) {

        if (level == 1) {
            return world.getSpawnPos();
        }

        int distance = config.mob_lvl_per_distance * level;

        BlockPos pos = new BlockPos(distance, 0, world.getSpawnPos()
            .getZ());

        return pos;

    }

    public static int determineLevelPerDistanceFromSpawn(World world, BlockPos pos) {
        return determineLevelPerDistanceFromSpawn(world, pos, SlashRegistry.getDimensionConfig(world));

    }

}
