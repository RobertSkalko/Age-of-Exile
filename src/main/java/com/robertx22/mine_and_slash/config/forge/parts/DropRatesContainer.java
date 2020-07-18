package com.robertx22.mine_and_slash.config.forge.parts;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;double;

public class DropRatesContainer {

    public double GEAR_DROPRATE;
    public double SKILL_GEM_DROPRATE;
    public double JEWEL_DROPRATE;
    public double UNIQUE_DROPRATE;
    public double CURRENCY_DROPRATE;
    public double COMPATIBLE_ITEMS_DROPRATE;

    public DropRatesContainer(ForgeConfigSpec.Builder builder) {
        builder.push("DROPRATES");

        GEAR_DROPRATE = builder.comment(".")
            .translation("mmorpg.config.gear_droprate")
            .defineInRange("GEAR_DROPRATE", 5F, 0, Integer.MAX_VALUE);

        SKILL_GEM_DROPRATE = builder.comment(".")
            .defineInRange("SKILL_GEM_DROPRATE", 2F, 0, Integer.MAX_VALUE);

        JEWEL_DROPRATE = builder.comment(".")
            .defineInRange("JEWEL_DROPRATE", 2F, 0, Integer.MAX_VALUE);

        UNIQUE_DROPRATE = builder.comment(".")
            .translation("mmorpg.config.unique_droprate")
            .defineInRange("UNIQUE_DROPRATE", 0.25F, 0, Integer.MAX_VALUE);

        CURRENCY_DROPRATE = builder.comment(".")
            .translation("mmorpg.config.currency_droprate")
            .defineInRange("CURRENCY_DROPRATE", 0.75F, 0, Integer.MAX_VALUE);

        COMPATIBLE_ITEMS_DROPRATE = builder.comment(".")
            .translation("mmorpg.config.compatible_items_droprate")
            .defineInRange("COMPATIBLE_ITEMS_DROPRATE", 2F, 0, Integer.MAX_VALUE);

        builder.pop();

    }

}
