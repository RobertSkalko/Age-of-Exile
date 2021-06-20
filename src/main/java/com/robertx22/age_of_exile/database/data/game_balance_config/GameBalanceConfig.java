package com.robertx22.age_of_exile.database.data.game_balance_config;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.JsonExileRegistry;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;

public class GameBalanceConfig implements JsonExileRegistry<GameBalanceConfig>, IAutoGson<GameBalanceConfig> {

    public static GameBalanceConfig SERIALIZER = new GameBalanceConfig();

    public String id = "game_balance";
    public static String ID = "game_balance";

    public static GameBalanceConfig get() {
        return (GameBalanceConfig) Database.getRegistry(ExileRegistryTypes.GAME_BALANCE)
            .get(ID);
    }

    public int MAX_LEVEL = 50;
    public int MAX_DUNGEON_TIER = 100;

    public LevelScalingConfig NORMAL_STAT_SCALING = new LevelScalingConfig(1, 0.5F, true);
    public LevelScalingConfig SLOW_STAT_SCALING = new LevelScalingConfig(1, 0.01F, true);
    public LevelScalingConfig MANA_COST_SCALING = new LevelScalingConfig(1, 0.1F, true);
    public LevelScalingConfig CORE_STAT_SCALING = new LevelScalingConfig(1, 0.05F, true);
    public LevelScalingConfig STAT_REQ_SCALING = new LevelScalingConfig(2, 2, true);

    public double TALENT_POINTS_AT_MAX_LEVEL = 125;
    public double STAT_POINTS_PER_LEVEL = 2;
    public double STARTING_TALENT_POINTS = 1;

    @Override
    public ExileRegistryTypes getExileRegistryType() {
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
