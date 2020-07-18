package com.robertx22.mine_and_slash.config.forge;

import net.minecraftforge.common.ForgeConfigSpec;

public class StatScaleConfigs {

    public LinearScalingConfig LINEAR_SCALING;
    public LinearScalingConfig SLOW_LINEAR_SCALING;

    public StatScaleConfigs(ForgeConfigSpec.Builder builder) {
        builder.push("STAT SCALING");

        LINEAR_SCALING = builder.comment("0.1F means every level will add 10% more stats. So lvl 1 sword has 11 dmg, lvl 2 12dmg, lvl 3 13dmg.. lvl 10 20 dmg etc")
            .configure((ForgeConfigSpec.Builder b) -> new LinearScalingConfig(b, "LINEAR", 0.05D))
            .getLeft();
        SLOW_LINEAR_SCALING = builder.comment("0.1F means every level will add 10% more stats. So lvl 1 sword has 11 dmg, lvl 2 12dmg, lvl 3 13dmg.. lvl 10 20 dmg etc")
            .configure((ForgeConfigSpec.Builder b) -> new LinearScalingConfig(b, "SLOW_LINEAR", 0.02D))
            .getLeft();

        builder.pop();
    }
}
