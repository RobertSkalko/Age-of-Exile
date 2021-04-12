package com.robertx22.age_of_exile.aoe_data.datapacks.generators;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;
import net.minecraft.item.Item;
import net.minecraft.loot.LootGsons;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class LootTableGenerator {

    protected DataCache cache;

    public LootTableGenerator() {

        try {
            cache = new DataCache(FabricLoader.getInstance()
                .getGameDir(), "datagencache");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Path getBasePath() {
        return FabricLoader.getInstance()
            .getGameDir();
    }

    protected Path movePath(Path target) {
        String movedpath = target.toString();
        movedpath = movedpath.replace("run/", "src/generated/resources");
        movedpath = movedpath.replace("run\\", "src/generated/resources");
        return Paths.get(movedpath);
    }

    private Path resolve(Path path, String id) {

        return path.resolve(
            "data/" + Ref.MODID + "/loot_tables/" + id
                + ".json");
    }

    public void run() {
        generateAll(cache);
    }

    static Gson GSON = LootGsons.getTableGsonBuilder()
        .setPrettyPrinting()
        .create();

    protected void generateAll(DataCache cache) {

        Path path = getBasePath();

        getLootTables().entrySet()
            .forEach(x -> {
                Path target = movePath(resolve(path, x.getKey()
                    .getPath()));

                try {
                    DataProvider.writeToPath(GSON, cache, GSON.toJsonTree(x.getValue()), target);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

    }

    public static Identifier RUNE_SALVAGE_RECIPE = Ref.id("runes_salvage_recipe");
    public static Identifier GEM_SALVAGE_RECIPE = Ref.id("gems_salvage_recipe");
    public static Identifier CURRENCIES_SALVAGE_RECIPE = Ref.id("currencies_salvage_recipe");

    private HashMap<Identifier, LootTable> getLootTables() {
        HashMap<Identifier, LootTable> map = new HashMap<Identifier, LootTable>();

        LootTable.Builder gems = LootTable.builder();
        LootPool.Builder gemloot = LootPool.builder();
        gemloot.rolls(UniformLootTableRange.between(1, 3));
        ModRegistry.GEMS.ALL.forEach(x -> {
            gemloot.with(ItemEntry.builder(x)
                .weight(x.weight));
        });
        gems.pool(gemloot);

        LootTable.Builder runes = LootTable.builder();
        LootPool.Builder runeloot = LootPool.builder();
        runeloot.rolls(UniformLootTableRange.between(1, 3));
        ModRegistry.RUNES.ALL.forEach(x -> {
            runeloot.with(ItemEntry.builder(x)
                .weight(x.weight));
        });
        runes.pool(runeloot);

        LootTable.Builder currencies = LootTable.builder();
        LootPool.Builder curLoot = LootPool.builder();
        curLoot.rolls(UniformLootTableRange.between(1, 3));
        ModRegistry.CURRENCIES.currencies.forEach(x -> {
            curLoot.with(ItemEntry.builder(x)
                .weight(x.Weight()));
        });
        currencies.pool(curLoot);

        map.put(RUNE_SALVAGE_RECIPE, runes.build());
        map.put(GEM_SALVAGE_RECIPE, gems.build());
        map.put(CURRENCIES_SALVAGE_RECIPE, currencies.build());

        ModRegistry.TIERED.FARMING_PRODUCE.values()
            .forEach(x -> {
                addFarming(ModRegistry.BLOCKS.FARMING_PLANTS.get(x.tier), x, 3, map);
            });

        addFarming(ModRegistry.BLOCKS.LIFE_PLANT, ModRegistry.MISC_ITEMS.LIFE_PLANT, 3, map);
        addFarming(ModRegistry.BLOCKS.MANA_PLANT, ModRegistry.MISC_ITEMS.MANA_PLANT, 3, map);

        return map;

    }

    private void addFarming(Block block, Item item, int age, HashMap<Identifier, LootTable> map) {

        LootCondition.Builder condition = BlockStatePropertyLootCondition.builder(block)
            .properties(StatePredicate.Builder.create()
                .exactMatch(CropBlock.AGE, age));

        LootTable.Builder b = LootTable.builder();
        LootPool.Builder loot = LootPool.builder();
        loot.conditionally(condition);
        loot.rolls(UniformLootTableRange.between(1, 3));

        loot.with(ItemEntry.builder(item));
        b.pool(loot);
        map.put(block.getLootTableId(), b.build());
    }

}
