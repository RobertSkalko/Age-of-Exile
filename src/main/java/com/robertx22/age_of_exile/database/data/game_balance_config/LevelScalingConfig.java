package com.robertx22.age_of_exile.database.data.game_balance_config;

import net.minecraft.util.math.MathHelper;

public class LevelScalingConfig {

    float base_scaling;
    float per_level_scaling;

    public LevelScalingConfig() {
    }

    public LevelScalingConfig(float base_scaling, float per_level_scaling) {
        this.base_scaling = base_scaling;
        this.per_level_scaling = per_level_scaling;
    }

    public float getMultiFor(int lvl) {
        lvl = MathHelper.clamp(lvl, 1, GameBalanceConfig.get().MAX_LEVEL);
        return base_scaling + (per_level_scaling * (lvl - 1));

    }

}
