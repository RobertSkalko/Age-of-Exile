package com.robertx22.mine_and_slash.config.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonConfig {
    public static final String NAME = "COMMON";
    public static final ForgeConfigSpec spec;
    public static final CommonConfig INSTANCE;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(
            CommonConfig::new);
        spec = specPair.getRight();
        INSTANCE = specPair.getLeft();

    }

    public ForgeConfigSpec.BooleanValue GET_STARTER_ITEMS;
    public ForgeConfigSpec.BooleanValue ENABLE_ANTI_MOB_FARM;

    CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Common Settings")
            .push(NAME);

        GET_STARTER_ITEMS = builder.comment(".")
            .translation("mmorpg.word")
            .define("GET_STARTER_ITEMS", true);

        ENABLE_ANTI_MOB_FARM = builder.comment("This is an experimental system that lowers drop rate based on chunk where mob is killed, it should have 0 impact towards normal gameplay but should heavily punish people who farm mobs in 1 big mob farm")
            .translation("mmorpg.word")
            .define("ENABLE_ANTI_MOB_FARM", true);

        builder.pop();
    }

}
