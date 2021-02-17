package com.robertx22.age_of_exile.aoe_data;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DirUtils;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.loot.JLootTable;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.function.ExplorationMapLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.world.gen.feature.StructureFeature;

import static net.devtech.arrp.json.loot.JLootTable.loot;
import static net.devtech.arrp.json.loot.JLootTable.pool;

public class LootTablesGen {
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("");

    public static void gen() {

        RESOURCE_PACK
            .addLootTable(Ref.id("test/testsuccess"),
                loot("minecraft:block")
                    .pool(pool()
                        .rolls(1)
                        .entry(JLootTable.entry()
                            .type("minecraft:item")
                            .name("minecraft:diamond"))
                        .condition(JLootTable.condition("minecraft:survives_explosion")))
            );

        System.out.println(LootGsons.getTableGsonBuilder()
            .setPrettyPrinting()
            .create()
            .toJson(LootTable.builder()
                .pool(LootPool.builder()
                    .rolls(UniformLootTableRange.between(2.0F, 8.0F))
                    .with(ItemEntry.builder(Items.COAL)
                        .weight(10)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(1.0F, 4.0F))))
                    .with(ItemEntry.builder(Items.GOLD_NUGGET)
                        .weight(10)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(1.0F, 3.0F))))
                    .with(ItemEntry.builder(Items.EMERALD))
                    .with(ItemEntry.builder(Items.WHEAT)
                        .weight(10)
                        .apply(SetCountLootFunction.builder(UniformLootTableRange.between(2.0F, 3.0F)))))
                .pool(LootPool.builder()
                    .rolls(ConstantLootTableRange.create(1))
                    .with(ItemEntry.builder(Items.GOLDEN_APPLE))
                    .with(ItemEntry.builder(Items.BOOK)
                        .weight(5)
                        .apply(EnchantRandomlyLootFunction.builder()))
                    .with(ItemEntry.builder(Items.LEATHER_CHESTPLATE))
                    .with(ItemEntry.builder(Items.GOLDEN_HELMET))
                    .with(ItemEntry.builder(Items.FISHING_ROD)
                        .weight(5)
                        .apply(EnchantRandomlyLootFunction.builder()))
                    .with(ItemEntry.builder(Items.MAP)
                        .weight(10)
                        .apply(ExplorationMapLootFunction.create()
                            .withDestination(StructureFeature.BURIED_TREASURE)
                            .withZoom((byte) 1)
                            .withSkipExistingChunks(false)))))
        );

        RESOURCE_PACK.dump(DirUtils.generatedResourcesDir()); // generates the files

    }
}
