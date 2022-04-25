package com.robertx22.age_of_exile.aoe_data.datapacks.generators;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.CurrencyItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RuneItems;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class LootTableGenerator {

    protected DirectoryCache cache;

    public LootTableGenerator() {

        try {
            cache = new DirectoryCache(FMLPaths.GAMEDIR.get(), "datagencache");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Path getBasePath() {
        return FMLPaths.GAMEDIR.get();
    }

    protected Path movePath(Path target) {
        String movedpath = target.toString();
        movedpath = movedpath.replace("run/", "src/generated/resources/");
        movedpath = movedpath.replace("run\\", "src/generated/resources/");
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
        CurrencyItems.getAllCurrenciesFromRegistry()
            .forEach(x -> {
                curLoot.add(ItemLootEntry.lootTableItem(x)
                    .setWeight(x
                        .Weight()));
            });
        currencies.withPool(curLoot);

        map.put(RUNE_SALVAGE_RECIPE, runes.build());
        map.put(CURRENCIES_SALVAGE_RECIPE, currencies.build());

        return map;

    }

}
