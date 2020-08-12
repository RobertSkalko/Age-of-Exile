package com.robertx22.age_of_exile.config.forge;

import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

public class StatScaleConfigs {

    @ConfigEntry.Gui.CollapsibleObject
    public LinearScalingConfig LINEAR_SCALING;
    @ConfigEntry.Gui.CollapsibleObject
    public LinearScalingConfig SLOW_LINEAR_SCALING;

    public StatScaleConfigs() {
        LINEAR_SCALING = new LinearScalingConfig(0.055D);
        SLOW_LINEAR_SCALING = new LinearScalingConfig(0.02D);
    }
}
