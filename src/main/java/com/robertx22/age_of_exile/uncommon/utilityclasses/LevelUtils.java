package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class LevelUtils {

    public static int getDistanceFromMaxLevel(int lvl) {

        int max = ModConfig.get().Server.MAX_LEVEL;

        return Math.abs(lvl - max);
    }

    public static float getMaxLevelMultiplier(int lvl) {
        float max = ModConfig.get().Server.MAX_LEVEL;
        return (float) lvl / max;
    }

    public static int getLevelForMultiplier(float multi) {
        return (int) (ModConfig.get().Server.MAX_LEVEL * multi);
    }

    public static int getExpRequiredForLevel(int level) {
        return (int) (Math.pow(6F * ModConfig.get().statScalings.NORMAL_STAT_SCALING.getMultiFor(level), 2.35F));
    }

    public static int getBaseExpMobReward(int level) {
        return 4 + (int) (Math.pow(2F * ModConfig.get().statScalings.NORMAL_STAT_SCALING.getMultiFor(level), 1.2F));
    }

    public static int determineLevel(World world, BlockPos pos, PlayerEntity nearestPlayer) {

        ServerWorld sw = (ServerWorld) world;

        DimensionConfig dimConfig = SlashRegistry.getDimensionConfig(world);

        int lvl = 0;

        if (ModConfig.get().Server.ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER || dimConfig.scale_to_nearest_player) {
            if (nearestPlayer != null) {
                lvl = Load.Unit(nearestPlayer)
                    .getLevel();
            } else {
                lvl = determineLevelPerDistanceFromSpawn(sw, pos, dimConfig);
            }
        } else {
            lvl = determineLevelPerDistanceFromSpawn(sw, pos, dimConfig);
        }

        lvl = MathHelper.clamp(dimConfig.min_lvl + lvl, dimConfig.min_lvl, dimConfig.max_lvl);

        lvl = MathHelper.clamp(lvl, 1, ModConfig.get().Server.MAX_LEVEL);

        return lvl;
    }

    public static int determineLevelPerDistanceFromSpawn(ServerWorld world, BlockPos pos, DimensionConfig config) {

        double distance = world.getSpawnPos()
            .getManhattanDistance(pos);

        double scale = MathHelper.clamp(world.getDimension()
            .getCoordinateScale() / 3F, 1, Integer.MAX_VALUE);

        distance *= scale;

        int lvl = 1;

        lvl = (int) (1 + (distance / (config.mob_lvl_per_distance)));

        return MathHelper.clamp(lvl, config.min_lvl, config.max_lvl);

    }

    public static double getBlocksForEachLevelDistance(ServerWorld world) {
        DimensionConfig config = SlashRegistry.getDimensionConfig(world);

        double scale = MathHelper.clamp(world.getDimension()
            .getCoordinateScale() / 3F, 1, Integer.MAX_VALUE);

        return config.mob_lvl_per_distance / scale;
    }

}
