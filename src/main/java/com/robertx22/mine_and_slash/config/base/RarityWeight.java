package com.robertx22.mine_and_slash.config.base;

import net.minecraftforge.common.ForgeConfigSpec;

public class RarityWeight {

    public ForgeConfigSpec.IntValue COMMON_WEIGHT;
    public ForgeConfigSpec.IntValue UNCOMMON_WEIGHT;
    public ForgeConfigSpec.IntValue RARE_WEIGHT;
    public ForgeConfigSpec.IntValue EPIC_WEIGHT;
    public ForgeConfigSpec.IntValue LEGENDARY_WEIGHT;

    public static class DefaultConfig {

        public int COMMON_WEIGHT = 30000;
        public int UNCOMMON_WEIGHT = 10000;
        public int RARE_WEIGHT = 2500;
        public int EPIC_WEIGHT = 250;
        public int LEGENDARY_WEIGHT = 50;

        public DefaultConfig higherChanceByMulti(float multi) {

            float anti = 1 / multi;

            COMMON_WEIGHT *= anti;
            UNCOMMON_WEIGHT *= anti;

            EPIC_WEIGHT *= multi;
            LEGENDARY_WEIGHT *= multi;

            return this;

        }

    }

    public RarityWeight(String prefix, ForgeConfigSpec.Builder builder, DefaultConfig config) {
        builder.push(prefix);

        COMMON_WEIGHT = builder.translation("mmorpg.rarity.common")
            .defineInRange("COMMON_WEIGHT", config.COMMON_WEIGHT, 0, Integer.MAX_VALUE);

        UNCOMMON_WEIGHT = builder.translation("mmorpg.rarity.uncommon")
            .defineInRange("UNCOMMON_WEIGHT", config.UNCOMMON_WEIGHT, 0, Integer.MAX_VALUE);

        RARE_WEIGHT = builder.translation("mmorpg.rarity.rare")
            .defineInRange("RARE_WEIGHT", config.RARE_WEIGHT, 0, Integer.MAX_VALUE);

        EPIC_WEIGHT = builder.translation("mmorpg.rarity.epic")
            .defineInRange("EPIC_WEIGHT", config.EPIC_WEIGHT, 0, Integer.MAX_VALUE);

        LEGENDARY_WEIGHT = builder.translation("mmorpg.rarity.legendary")
            .defineInRange("LEGENDARY_WEIGHT", config.LEGENDARY_WEIGHT, 0, Integer.MAX_VALUE);

        builder.pop();
    }

}