package com.robertx22.mine_and_slash.config.forge.parts;

import com.robertx22.mine_and_slash.config.base.RarityWeight;
import net.minecraftforge.common.ForgeConfigSpec;

public class RarityDropratesConfig {

    public RarityWeight MOBS;
    public RarityWeight CURRENCY;

    public RarityDropratesConfig(ForgeConfigSpec.Builder builder) {
        builder.push("RARITY_WEIGHT_CONTAINERS");

        RarityWeight.DefaultConfig config = new RarityWeight.DefaultConfig();

        MOBS = builder.configure(
            (ForgeConfigSpec.Builder prefix) -> new RarityWeight("MOBS", builder,
                new RarityWeight.DefaultConfig().higherChanceByMulti(1.4F)))
            .getLeft();

        CURRENCY = builder.configure((ForgeConfigSpec.Builder prefix) -> new RarityWeight("CURRENCY", builder,
            new RarityWeight.DefaultConfig()
        ))
            .getLeft();

        builder.pop();
    }

}
