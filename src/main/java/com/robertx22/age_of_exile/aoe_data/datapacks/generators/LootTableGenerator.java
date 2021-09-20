package com.robertx22.age_of_exile.aoe_data.datapacks.generators;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.*;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.loading.FMLLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class LootTableGenerator {

    protected DirectoryCache cache;

    public LootTableGenerator() {

        try {
            cache = new DirectoryCache(FMLLoader.getGamePath(), "datagencache");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Path getBasePath() {
        return FMLLoader.getGamePath();
    }

    protected Path movePath(Path target) {
        String movedpath = target.toString();
        movedpath = movedpath.replace("run/", "src/generated/resources");
        movedpath = movedpath.replace("run\\", "src/generated/resources");
        return Paths.get(movedpath);
    }

    private Path resolve(Path path, String id) {

        return path.resolve(
            "data/" + SlashRef.MODID + "/loot_tables/" + id
                + ".json");
    }

    public void run() {
        generateAll(cache);
    }

    static Gson GSON = LootSerializers.createLootTableSerializer()
        .setPrettyPrinting()
        .create();

    protected void generateAll(DirectoryCache cache) {

        Path path = getBasePath();

        getLootTables().entrySet()
            .forEach(x -> {
                Path target = movePath(resolve(path, x.getKey()
                    .getPath()));

                try {
                    IDataProvider.save(GSON, cache, GSON.toJsonTree(x.getValue()), target);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

    }

    public static ResourceLocation RUNE_SALVAGE_RECIPE = SlashRef.id("runes_salvage_recipe");
    public static ResourceLocation GEM_SALVAGE_RECIPE = SlashRef.id("gems_salvage_recipe");
    public static ResourceLocation CURRENCIES_SALVAGE_RECIPE = SlashRef.id("currencies_salvage_recipe");

    private HashMap<ResourceLocation, LootTable> getLootTables() {
        HashMap<ResourceLocation, LootTable> map = new HashMap<ResourceLocation, LootTable>();

        LootTable.Builder gems = LootTable.lootTable();
        LootPool.Builder gemloot = LootPool.lootPool();
        gemloot.setRolls(RandomValueRange.between(1, 3));
        GemItems.ALL.forEach(x -> {
            gemloot.add(ItemLootEntry.lootTableItem(x.get())
                .setWeight(x.get().weight));
        });
        gems.withPool(gemloot);

        LootTable.Builder runes = LootTable.lootTable();
        LootPool.Builder runeloot = LootPool.lootPool();
        runeloot.setRolls(RandomValueRange.between(1, 3));
        RuneItems.ALL.forEach(x -> {
            runeloot.add(ItemLootEntry.lootTableItem(x.get())
                .setWeight(x.get().weight));
        });
        runes.withPool(runeloot);

        LootTable.Builder currencies = LootTable.lootTable();
        LootPool.Builder curLoot = LootPool.lootPool();
        curLoot.setRolls(RandomValueRange.between(1, 3));
        CurrencyItems.currencies.forEach(x -> {
            curLoot.add(ItemLootEntry.lootTableItem(x.get())
                .setWeight(x.get()
                    .Weight()));
        });
        currencies.withPool(curLoot);

        map.put(RUNE_SALVAGE_RECIPE, runes.build());
        map.put(GEM_SALVAGE_RECIPE, gems.build());
        map.put(CURRENCIES_SALVAGE_RECIPE, currencies.build());

        ProfessionItems.FARMING_PRODUCE.values()
            .forEach(x -> {
                addFarming(SlashBlocks.FARMING_PLANTS.get(x.get().tier)
                    .get(), x.get(), null, 3, map);
            });

        addFarming(SlashBlocks.LIFE_PLANT.get(), SlashItems.HP_FLOWER_SEED.get(), SlashItems.LIFE_PLANT.get(), 3, map)
        ;
        addFarming(SlashBlocks.MANA_PLANT.get(), SlashItems.MANA_FLOWER_SEED.get(), SlashItems.MANA_PLANT.get(), 3, map);

        return map;

    }

    private void addFarming(Block block, Item item, Item seed, int age, HashMap<ResourceLocation, LootTable> map) {

        ILootCondition.IBuilder condition = BlockStateProperty.hasBlockStateProperties(block)
            .setProperties(StatePropertiesPredicate.Builder.properties()
                .hasProperty(CropsBlock.AGE, age));

        LootTable.Builder b = LootTable.lootTable();

        LootPool.Builder loot = LootPool.lootPool();
        loot.when(condition);
        loot.setRolls(RandomValueRange.between(1, 3));
        loot.add(ItemLootEntry.lootTableItem(item));
        b.withPool(loot);

        if (seed != null) {
            LootPool.Builder seedpool = LootPool.lootPool();
            seedpool.when(condition);
            seedpool.setRolls(RandomValueRange.between(1, 2));
            seedpool.add(ItemLootEntry.lootTableItem(seed));
            b.withPool(seedpool);
        }

        map.put(block.getLootTable(), b.build());
    }

}
