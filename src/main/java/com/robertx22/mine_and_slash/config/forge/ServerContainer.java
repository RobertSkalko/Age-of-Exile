package com.robertx22.mine_and_slash.config.forge;

import net.minecraft.entity.EntityType;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraftforge.common.ForgeConfigSpec.*;

public class ServerContainer {

    public BooleanValue USE_COMPATIBILITY_ITEMS;
    public BooleanValue LOG_REGISTRY_ENTRIES;

    public DoubleValue REPAIR_FUEL_NEEDED_MULTI;
    public DoubleValue REGEN_HUNGER_COST;
    public DoubleValue EXP_LOSS_ON_DEATH;
    public DoubleValue STAT_POINTS_PER_LVL;
    public DoubleValue VANILLA_ARMOR_EFFECTIVENESS;

    public IntValue MAX_LEVEL;

    public ConfigValue<List<? extends String>> IGNORED_ENTITIES;

    ServerContainer(Builder builder) {
        builder.push("GENERAL");

        List<EntityType> list = new ArrayList<>();
        list.add(EntityType.BAT);
        list.add(EntityType.TROPICAL_FISH);
        list.add(EntityType.PUFFERFISH);
        list.add(EntityType.SALMON);
        list.add(EntityType.COD);
        list.add(EntityType.SQUID);
        list.add(EntityType.RABBIT);

        List<String> idlist = list.stream()
            .map(x -> x.getRegistryName()
                .toString())
            .collect(Collectors.toList());

        MAX_LEVEL = builder.comment(".")
            .translation("mmorpg.word.")
            .defineInRange("MAX_LEVEL", 50, 0, 10000);

        IGNORED_ENTITIES = builder.comment(".")
            .translation("mmorpg.word.")
            .defineList("IGNORED_ENTITIES", idlist, x -> true);

        REGEN_HUNGER_COST = builder.comment(".")
            .translation("mmorpg.word.")
            .defineInRange("REGEN_HUNGER_COST", 20D, 0, 1000D);

        VANILLA_ARMOR_EFFECTIVENESS = builder.comment("As this mod uses it's own armor for entity damage, enabling this will make the player op. 1 Means 100% effective 0.5 means half effective")
            .defineInRange("VANILLA_ARMOR_EFFECTIVENESS", 0, 0, 1F);

        STAT_POINTS_PER_LVL = builder.comment(".")
            .translation("mmorpg.word.")
            .defineInRange("STAT_POINTS_PER_LVL", 1, 0, 50D);

        EXP_LOSS_ON_DEATH = builder.comment(".")
            .translation("mmorpg.word.")
            .defineInRange("EXP_LOSS_ON_DEATH", 0.05F, 0, 1000D);

        REPAIR_FUEL_NEEDED_MULTI = builder.comment(".")
            .translation("mmorpg.word.")
            .defineInRange("REPAIR_FUEL_NEEDED_MULTI", 1D, 0, 100000D);

        LOG_REGISTRY_ENTRIES = builder.comment(".")
            .define("LOG_REGISTRY_ENTRIES", false);

        USE_COMPATIBILITY_ITEMS = builder.comment(".")
            .translation("mmorpg.word.")
            .define("USE_COMPATIBILITY_ITEMS", true);

        builder.pop();
    }

}
