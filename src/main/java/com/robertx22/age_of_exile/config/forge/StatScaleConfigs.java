package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.config.forge.parts.LevelScalingConfig;
import com.robertx22.age_of_exile.config.forge.parts.LevelScalingRangePart;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

public class StatScaleConfigs {

    @ConfigEntry.Gui.CollapsibleObject
    public LevelScalingConfig NORMAL_STAT_SCALING;
    @ConfigEntry.Gui.CollapsibleObject
    public LevelScalingConfig SLOW_STAT_SCALING;

    public StatScaleConfigs() {

        List<LevelScalingRangePart> linear = new ArrayList<>();
        linear.add(new LevelScalingRangePart(0F, 0.2F, 1.1F, 0.5F));
        linear.add(new LevelScalingRangePart(0.2F, 0.4F, 0.5F, 2F));
        linear.add(new LevelScalingRangePart(0.4F, 0.8F, 2F, 15));
        linear.add(new LevelScalingRangePart(0.8F, 1F, 15, 30));

        List<LevelScalingRangePart> slow = new ArrayList<>();
        slow.add(new LevelScalingRangePart(0F, 1F, 1, 2F));

        NORMAL_STAT_SCALING = new LevelScalingConfig(linear);
        SLOW_STAT_SCALING = new LevelScalingConfig(slow);
    }
}
