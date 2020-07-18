package com.robertx22.mine_and_slash.config.forge;

import net.minecraftforge.common.ForgeConfigSpec;

public class StatScaleValues {

    public ForgeConfigSpec.DoubleValue FIRST_VALUE;
    public ForgeConfigSpec.DoubleValue SECOND_VALUE;
    public ForgeConfigSpec.DoubleValue THIRD_VALUE;
    public ForgeConfigSpec.DoubleValue FOURTH_VALUE;

    StatScaleValues(ForgeConfigSpec.Builder builder, String name, Double first, Double second, Double third,
                    Double fourth) {
        builder.push(name);

        float min = -1000;
        float max = 1000;

        FIRST_VALUE = builder.comment(".")
            .defineInRange("FIRST_VAL", first, min, max);
        SECOND_VALUE = builder.comment(".")
            .defineInRange("SECOND_VAL", second, min, max);
        THIRD_VALUE = builder.comment(".")
            .defineInRange("THIRD_VAL", third, min, max);
        FOURTH_VALUE = builder.comment(".")
            .defineInRange("FOURTH_VAL", fourth, min, max);

        builder.pop();
    }
}