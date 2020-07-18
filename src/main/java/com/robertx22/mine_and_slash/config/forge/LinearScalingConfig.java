package com.robertx22.mine_and_slash.config.forge;

import net.minecraftforge.common.ForgeConfigSpec;

public class LinearScalingConfig {

    public ForgeConfigSpec.DoubleValue PERCENT_ADDED_PER_LEVEL;

    LinearScalingConfig(ForgeConfigSpec.Builder builder, String name, Double first) {
        builder.push(name);

        float min = -1000;
        float max = 1000;

        PERCENT_ADDED_PER_LEVEL = builder.comment(".")
            .defineInRange("PERCENT_ADDED_PER_LEVEL", first, min, max);

        builder.pop();
    }
}
