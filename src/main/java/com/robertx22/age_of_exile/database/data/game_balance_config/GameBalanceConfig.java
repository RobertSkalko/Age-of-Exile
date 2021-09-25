package com.robertx22.age_of_exile.database.data.game_balance_config;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;

import java.util.HashMap;

public class GameBalanceConfig implements JsonExileRegistry<GameBalanceConfig>, IAutoGson<GameBalanceConfig> {

    public static GameBalanceConfig SERIALIZER = new GameBalanceConfig();

    public String id = "game_balance";
    public static String ID = "game_balance";

    public static GameBalanceConfig get() {
        return (GameBalanceConfig) Database.getRegistry(ExileRegistryTypes.GAME_BALANCE)
            .get(ID);
    }

    public int MAX_LEVEL = 50;

    public LevelScalingConfig NORMAL_STAT_SCALING = new LevelScalingConfig(1, 0.5F, true);
    public LevelScalingConfig SLOW_STAT_SCALING = new LevelScalingConfig(1, 0.02F, true);
    public LevelScalingConfig MANA_COST_SCALING = new LevelScalingConfig(1, 0.2F, true);
    public LevelScalingConfig CORE_STAT_SCALING = new LevelScalingConfig(1, 0.05F, true);
    public LevelScalingConfig STAT_REQ_SCALING = new LevelScalingConfig(2, 2, true);

    public double TALENT_POINTS_AT_MAX_LEVEL = 100;
    public double STAT_POINTS_PER_LEVEL = 2;
    public double SPELL_POINTS_PER_LEVEL = 2;
    public double STARTING_TALENT_POINTS = 1;

    public int levels_per_tier = 10;

    public HashMap<MinMax, Integer> getTierMap() {

        HashMap<MinMax, Integer> tiermap = new HashMap<>();

        int tier = 1;
        int lvl = 0;

        while (lvl < MAX_LEVEL) {

            int min = ((tier - 1) * levels_per_tier) - 1;
            if (min < 0) {
                min = 0;
            }
            int max = tier * levels_per_tier;

            lvl += levels_per_tier;

            if (lvl == MAX_LEVEL) {
                max += 100;
            }

            tiermap.put(new MinMax(min, max), tier);

            tier++;

        }

        return tiermap;

    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GAME_BALANCE;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<GameBalanceConfig> getClassForSerialization() {
        return GameBalanceConfig.class;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
