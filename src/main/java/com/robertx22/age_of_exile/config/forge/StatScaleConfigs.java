package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.config.forge.parts.LevelScalingConfig;
import com.robertx22.age_of_exile.config.forge.parts.LevelScalingRangePart;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

import java.util.Arrays;

public class StatScaleConfigs {

    @ConfigEntry.Gui.CollapsibleObject
    public LevelScalingConfig NORMAL_STAT_SCALING;

    @ConfigEntry.Gui.CollapsibleObject
    public LevelScalingConfig SLOW_STAT_SCALING;

    @ConfigEntry.Gui.CollapsibleObject
    public LevelScalingConfig MANA_COST_SCALING;

    public StatScaleConfigs() {

        NORMAL_STAT_SCALING = new LevelScalingConfig(Arrays.asList(new LevelScalingRangePart(0F, 1F, 1, 50)));

        SLOW_STAT_SCALING = new LevelScalingConfig(Arrays.asList(new LevelScalingRangePart(0F, 1F, 1, 1.25F)));

        MANA_COST_SCALING = new LevelScalingConfig(Arrays.asList(new LevelScalingRangePart(0F, 1F, 1, 10)));

    }
}


