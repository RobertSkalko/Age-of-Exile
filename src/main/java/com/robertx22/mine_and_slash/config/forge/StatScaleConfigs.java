package com.robertx22.mine_and_slash.config.forge;

public class StatScaleConfigs {

    public LinearScalingConfig LINEAR_SCALING;
    public LinearScalingConfig SLOW_LINEAR_SCALING;

    public StatScaleConfigs() {

        LINEAR_SCALING = new LinearScalingConfig(0.05D);

        SLOW_LINEAR_SCALING = new LinearScalingConfig(0.02D);

    }
}
