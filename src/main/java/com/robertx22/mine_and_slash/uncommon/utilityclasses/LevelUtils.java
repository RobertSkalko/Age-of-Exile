package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.data.DimensionConfig;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class LevelUtils {

    public static int getExpRequiredForLevel(int level) {
        return level * level * 10;
    }

    // dirty hack, let's see how useful you are.
    public static float getExpDropForLevel(LivingEntity entity, int level) {
        float hp = EntityUtils.getVanillaMaxHealth(entity);
        return (float) (hp + (hp * 0.075 * level));
    }

    public static int determineLevel(World world, BlockPos pos, PlayerEntity nearestPlayer) {

        ServerWorld sw = (ServerWorld) world;

        DimensionConfig dimConfig = SlashRegistry.getDimensionConfig(world);

        int lvl = 0;

        if (dimConfig.scale_to_nearest_player) {
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

        return lvl;
    }

    public static int determineLevelPerDistanceFromSpawn(ServerWorld world, BlockPos pos, DimensionConfig config) {

        BlockPos spawnPos = new BlockPos(0, 70, 0); // todo

        double distance = world.getSpawnPos()
            .getManhattanDistance(pos);

        int lvl = 1;

        lvl = (int) (1 + (distance / config.mob_lvl_per_distance));

        return MathHelper.clamp(lvl, config.min_lvl, config.max_lvl);

    }

    public static BlockPos getAreaPosOfLevel(ServerWorld world, int level, DimensionConfig config) {

        if (level == 1) {
            return world.getSpawnPos();
        }

        int distance = config.mob_lvl_per_distance * level;

        BlockPos pos = new BlockPos(distance, 0, world.getSpawnPos()
            .getZ());

        return pos;

    }

    public static int determineLevelPerDistanceFromSpawn(ServerWorld world, BlockPos pos) {
        return determineLevelPerDistanceFromSpawn(world, pos, SlashRegistry.getDimensionConfig(world));

    }

}
