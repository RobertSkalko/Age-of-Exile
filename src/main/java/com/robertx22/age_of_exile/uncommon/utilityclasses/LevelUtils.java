package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.*;
import java.util.stream.Stream;

public class LevelUtils {

    static boolean isTier(MinMax range, int lvl) {
        return lvl >= range.min && lvl <= range.max;
    }

    static Set<Integer> cachedTiers = new HashSet<>();

    public static int getMaxTier() {
        return levelToTier(GameBalanceConfig.get().MAX_LEVEL);
    }

    public static Set<Integer> getAllTiers() {

        if (cachedTiers.isEmpty()) {

            int maxlvl = GameBalanceConfig.get().MAX_LEVEL;

            Set<Integer> tiers = new HashSet<>();

            for (int i = 0; i < maxlvl; i++) {
                tiers.add(levelToTier(i));
            }

            cachedTiers = tiers;
        }

        return cachedTiers;
    }

    public static void runTests() {
        if (MMORPG.RUN_DEV_TOOLS) {
            Preconditions.checkArgument(levelToTierToLevel(1) == 1);
            Preconditions.checkArgument(levelToTierToLevel(20) == 20);
            Preconditions.checkArgument(levelToTierToLevel(40) == 40);
            Preconditions.checkArgument(levelToTierToLevel(100) == 100);
        }
    }

    public static int levelToTierToLevel(int level) {

        int tier = levelToTier(level);

        int lvl = tierToLevel(tier);

        return lvl;

    }

    public static int tierToLevel(int tier) {
        int lvl = 1;
        try {
            Optional<Map.Entry<MinMax, Integer>> opt = GameBalanceConfig.get()
                .getTierMap()
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == tier)
                .findAny();

            if (opt.isPresent()) {
                lvl = opt.get()
                    .getKey().min;
            } else {
                if (tier > 0) {
                    return GameBalanceConfig.get().MAX_LEVEL;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        if (lvl < 1) {
            lvl = 1;
        }

        return lvl;
    }

    public static int levelToTier(int level) {
        int tier = 0;
        try {
            Stream<Map.Entry<MinMax, Integer>> opt = GameBalanceConfig.get()
                .getTierMap()
                .entrySet()
                .stream()
                .filter(e -> isTier(e.getKey(), level));

            OptionalInt biggest = opt.mapToInt(x -> x.getValue())
                .sorted()
                .max();

            if (biggest.isPresent()) {
                tier = biggest.getAsInt();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return tier;

    }

    public static SkillItemTier levelToSkillTier(int lvl) {
        for (SkillItemTier tier : SkillItemTier.values()) {
            if (tier.levelRange.isLevelInRange(lvl)) {
                return tier;
            }
        }
        return SkillItemTier.TIER4; // if it's neither, it's above max lvl, means we give max tier
    }

    public static int getDistanceFromMaxLevel(int lvl) {
        int max = GameBalanceConfig.get().MAX_LEVEL;
        return Math.abs(lvl - max);
    }

    public static float getMaxLevelMultiplier(float lvl) {
        float max = GameBalanceConfig.get().MAX_LEVEL;
        return (float) lvl / max;
    }

    public static int getExpRequiredForLevel(int level) {
        return (int) (Math.pow(10F * GameBalanceConfig.get().NORMAL_STAT_SCALING.getMultiFor(level), 2.1F));
    }

    public static int getExpNeededForSkillLevel(int level) {
        float exponent = 0.1F * (float) level / (float) GameBalanceConfig.get().MAX_LEVEL;
        exponent = MathHelper.clamp(exponent, 0, 0.15F);
        return (int) Math.pow(25 + (level * 20), 1 + exponent);
    }

    public static int getBaseExpMobReward(int level) {
        return 10 + scaleExpReward(3, level);
    }

    public static int scaleExpReward(int exp, int level) {
        return (int) (Math.pow(exp * GameBalanceConfig.get().NORMAL_STAT_SCALING.getMultiFor(level), 1.1F));
    }

    public static class LevelDetermInfo {

        public int levelWithoutScalingDifficulty;
        public int level;

    }

    public static LevelDetermInfo determineLevel(World world, BlockPos pos, PlayerEntity nearestPlayer) {

        LevelDetermInfo info = new LevelDetermInfo();

        ServerWorld sw = (ServerWorld) world;

        DimensionConfig dimConfig = ExileDB.getDimensionConfig(world);

        int lvl = 0;

        if (ServerContainer.get().ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER.get() || dimConfig.scale_to_nearest_player) {
            if (nearestPlayer != null) {

                if (isInMinLevelArea(sw, pos, dimConfig)) {
                    lvl = dimConfig.min_lvl;
                } else {
                    lvl = Load.Unit(nearestPlayer)
                        .getLevel();
                }
            } else {
                lvl = determineLevelPerDistanceFromSpawn(sw, pos, dimConfig);
            }
        } else {
            lvl = determineLevelPerDistanceFromSpawn(sw, pos, dimConfig);
        }

        lvl = MathHelper.clamp(lvl, dimConfig.min_lvl, dimConfig.max_lvl);
        lvl = MathHelper.clamp(lvl, 1, GameBalanceConfig.get().MAX_LEVEL);

        info.levelWithoutScalingDifficulty = lvl;

        if (nearestPlayer != null) {
            int bonusDiffLevel = Load.playerRPGData(nearestPlayer).scalingDifficulty.getBonusLevels();

            int max = Load.Unit(nearestPlayer)
                .getLevel() + ServerContainer.get().MAX_MOB_LVL_HIGHER_THAN_PLAYER_FOR_DIFF.get();

            int cangive = max - lvl;
            if (cangive < 0) {
                cangive = 0;
            }

            if (bonusDiffLevel > cangive) {
                bonusDiffLevel = cangive;
            }

            lvl += bonusDiffLevel;
        }

        lvl = MathHelper.clamp(lvl, dimConfig.min_lvl, dimConfig.max_lvl);
        lvl = MathHelper.clamp(lvl, 1, GameBalanceConfig.get().MAX_LEVEL);

        info.level = lvl;

        return info;
    }

    public static boolean isInMinLevelArea(ServerWorld world, BlockPos pos, DimensionConfig config) {
        double distance = world.getSharedSpawnPos()
            .distManhattan(pos);

        double scale = MathHelper.clamp(world.dimensionType()
            .coordinateScale() / 3F, 1, Integer.MAX_VALUE);

        distance *= scale;

        if (distance < config.min_lvl_area) {
            return true;
        }
        return false;
    }

    public static int determineLevelPerDistanceFromSpawn(ServerWorld world, BlockPos pos, DimensionConfig config) {

        double distance = world.getSharedSpawnPos()
            .distManhattan(pos);

        double scale = MathHelper.clamp(world.dimensionType()
            .coordinateScale() / 3F, 1, Integer.MAX_VALUE);

        distance *= scale;

        if (distance < config.min_lvl_area) {
            return config.min_lvl;
        }

        int lvl = 1;

        lvl = (int) (config.min_lvl + ((distance - config.min_lvl_area) / (config.mob_lvl_per_distance)));

        return MathHelper.clamp(lvl, config.min_lvl, config.max_lvl);

    }

    public static double getBlocksForEachLevelDistance(ServerWorld world) {
        DimensionConfig config = ExileDB.getDimensionConfig(world);

        double scale = MathHelper.clamp(world.dimensionType()
            .coordinateScale() / 3F, 1, Integer.MAX_VALUE);

        return config.mob_lvl_per_distance / scale;
    }

}
