package com.robertx22.age_of_exile.config.forge.parts;

import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;

public class LevelScalingRangePart {

    float min_lvl;
    float max_lvl;
    float stat_growth_min;
    float stat_growth_max;

    public LevelScalingRangePart() {
    }

    public LevelScalingRangePart(float min_lvl, float max_lvl, float stat_growth_min, float stat_growth_max) {
        this.min_lvl = min_lvl;
        this.max_lvl = max_lvl;
        this.stat_growth_min = stat_growth_min;
        this.stat_growth_max = stat_growth_max;
    }

    public boolean isForLevel(int lvl) {
        float lvlmulti = LevelUtils.getMaxLevelMultiplier(lvl);
        return lvlmulti >= min_lvl && lvlmulti <= max_lvl;
    }

    public float getGrowthForLevel(int lvl) {
        // TODO this might be too damn laggy!!!
        int min = LevelUtils.getLevelForMultiplier(min_lvl);
        int max = LevelUtils.getLevelForMultiplier(max_lvl);
        int left = lvl - min;
        float multi = (float) left / (float) (max - min);
        return stat_growth_min + (multi * (stat_growth_max - stat_growth_min));
    }
}
