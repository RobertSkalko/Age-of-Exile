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

        List<LevelScalingRangePart> normal = new ArrayList<>();
        normal.add(new LevelScalingRangePart(0F, 0.2F, 1.1F, 2F));
        normal.add(new LevelScalingRangePart(0.2F, 0.4F, 2F, 10F));
        normal.add(new LevelScalingRangePart(0.4F, 0.8F, 10F, 40));
        normal.add(new LevelScalingRangePart(0.8F, 1F, 40, 100));

        List<LevelScalingRangePart> kills = new ArrayList<>();
        kills.add(new LevelScalingRangePart(0F, 0.2F, 2, 15));
        kills.add(new LevelScalingRangePart(0.2F, 0.4F, 15, 40));
        kills.add(new LevelScalingRangePart(0.4F, 0.8F, 40, 100));
        kills.add(new LevelScalingRangePart(0.8F, 1F, 100, 2500));

        List<LevelScalingRangePart> slow = new ArrayList<>();
        slow.add(new LevelScalingRangePart(0F, 1F, 1, 1.25F));

        NORMAL_STAT_SCALING = new LevelScalingConfig(normal);
        SLOW_STAT_SCALING = new LevelScalingConfig(slow);
    }
}
