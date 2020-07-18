package com.robertx22.mine_and_slash.config.forge.parts;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class DmgParticleConfig {

    public ForgeConfigSpec.BooleanValue ENABLE_CHAT_EXP_MSG;
    public ForgeConfigSpec.BooleanValue ENABLE_FLOATING_DMG;

    public ConfigValue<Double> GRAVITY;
    public ConfigValue<Double> START_SIZE;
    public ConfigValue<Double> MAX_SIZE;
    public ConfigValue<Double> LIFESPAN;
    public ConfigValue<Double> SPEED;

    public ConfigValue<Boolean> GROWS;

    public DmgParticleConfig(ForgeConfigSpec.Builder builder) {
        builder.push("DMG_PARTICLE_CONFIGS");

        GRAVITY = builder.define("GRAVITY", 0.15D);
        START_SIZE = builder.define("START_SIZE", 0.5D);
        MAX_SIZE = builder.define("MAX_SIZE", 2D);
        LIFESPAN = builder.define("LIFESPAN", 12D);
        SPEED = builder.define("SPEED", 0.003D);

        GROWS = builder.define("GROWS", true);

        ENABLE_CHAT_EXP_MSG = builder.comment(".")
            .translation("mmorpg.config.floating_exp")
            .define("ENABLE_FLOATING_EXP", true);

        ENABLE_FLOATING_DMG = builder.comment(".")
            .translation("mmorpg.config.floating_damage_numbers")
            .define("ENABLE_FLOATING_DMG", true);

        builder.pop();
    }

}
