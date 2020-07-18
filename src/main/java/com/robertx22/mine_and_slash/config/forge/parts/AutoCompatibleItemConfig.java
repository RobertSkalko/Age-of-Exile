package com.robertx22.mine_and_slash.config.forge.parts;

import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraftforge.common.ForgeConfigSpec;

public class AutoCompatibleItemConfig {

    public ForgeConfigSpec.BooleanValue ENABLE_AUTOMATIC_COMPATIBLE_ITEMS;

    public ForgeConfigSpec.IntValue MAX_SINGLE_STAT_VALUE;
    public ForgeConfigSpec.IntValue MAX_TOTAL_STATS;

    public AutoConfigItemType HORRIBLE;
    public AutoConfigItemType TRASH;
    public AutoConfigItemType NORMAL;
    public AutoConfigItemType BEST;

    public AutoCompatibleItemConfig(ForgeConfigSpec.Builder builder) {
        builder.push("AUTO_ITEM_COMPATIBILITY");

        ENABLE_AUTOMATIC_COMPATIBLE_ITEMS = builder.comment("This automatically makes items compatible that i can recognize automatically, meaning they extend from vanilla classes.")
            .define("ENABLE_AUTOMATIC_COMPATIBLE_ITEMS", false);

        MAX_TOTAL_STATS = builder.comment("Used in power level calculation. Total stats of an item are capped to this value to prevent the median being offset too much by god items.")
            .defineInRange("MAX_TOTAL_STATS", 200, 0, 100000);
        MAX_SINGLE_STAT_VALUE = builder.defineInRange("MAX_SINGLE_STAT_VALUE", 50, 0, 100000);

        HORRIBLE = builder.comment("")
            .configure((ForgeConfigSpec.Builder b) -> {
                return new AutoConfigItemType(0F, b, "HORRIBLE", 20, IRarity.Common, IRarity.Common);
            })
            .getLeft();
        TRASH = builder.comment("")
            .configure((ForgeConfigSpec.Builder b) -> {
                return new AutoConfigItemType(0.03F, b, "TRASH", 40, IRarity.Common, IRarity.Magical);
            })
            .getLeft();
        NORMAL = builder.comment("")
            .configure((ForgeConfigSpec.Builder b) -> {
                return new AutoConfigItemType(0.3F, b, "NORMAL", 80, IRarity.Magical, IRarity.Magical);
            })
            .getLeft();
        BEST = builder.comment("")
            .configure((ForgeConfigSpec.Builder b) -> {
                return new AutoConfigItemType(0.8F, b, "BEST", Integer.MAX_VALUE, IRarity.Magical, IRarity.Rare);
            })
            .getLeft();

        builder.pop();
    }

}
