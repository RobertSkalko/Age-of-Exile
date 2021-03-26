package com.robertx22.age_of_exile.aoe_data.datapacks.generators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.recipe_types.IStationRecipe;
import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import joptsimple.internal.Strings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancement.criterion.EnchantedItemCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonFactory;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class RecipeGenerator {

    public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting()
        .create();
    protected DataCache cache;

    public RecipeGenerator() {

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
        movedpath = movedpath.replace("run", "src/generated/resources");
        return Paths.get(movedpath);
    }

    private Path resolve(Path path, String id) {

        return path.resolve(
            "data/" + Ref.MODID + "/recipes/" + id
                + ".json");
    }

    public void run() {
        generateAll(cache);
    }

    protected void generateAll(DataCache cache) {

        Path path = getBasePath();

        generate(x -> {

            Path target = movePath(resolve(path, x.getRecipeId()
                .getPath()));

            try {
                DataProvider.writeToPath(GSON, cache, x.toJson(), target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

    private void generate(Consumer<RecipeJsonProvider> consumer) {
        for (Item item : Registry.ITEM) {
            if (item instanceof IShapedRecipe) {
                IShapedRecipe ir = (IShapedRecipe) item;
                ShapedRecipeJsonFactory rec = ir.getRecipe();
                if (rec != null) {
                    rec.offerTo(consumer);
                }

            }
            if (item instanceof IShapelessRecipe) {
                IShapelessRecipe sr = (IShapelessRecipe) item;
                ShapelessRecipeJsonFactory srec = sr.getRecipe();
                if (srec != null) {
                    try {
                        srec.offerTo(consumer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (item instanceof IStationRecipe) {
                IStationRecipe sr = (IStationRecipe) item;
                StationShapelessFactory srec = sr.getStationRecipe();
                if (srec != null) {
                    try {
                        srec.offerTo(consumer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        ModRegistry.TIERED.SMELTED_ESSENCE.values()
            .forEach(x -> {
                Item ess = ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(x.tier);
                CookingRecipeJsonFactory.createSmelting(Ingredient.ofItems(ess), x, 0.2F, 200)
                    .criterion("ess" + x.tier, conditionsFromItem(ess))
                    .offerTo(consumer);
            });

        ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.values()
            .forEach(x -> {
                if (x.tier.lowerTier() != null) {
                    ShapelessRecipeJsonFactory fac = ShapelessRecipeJsonFactory.create(x, 1);
                    fac.input(ModRegistry.TIERED.SALVAGED_ESSENCE_MAP.get(x.tier.lowerTier()), 4);
                    fac.criterion("player_level", EnchantedItemCriterion.Conditions.any())
                        .offerTo(consumer);
                }
            });

        Database.GearTypes()
            .getSerializable()
            .forEach(x -> {

                if (Registry.ITEM.getId(x.getItem())
                    .getNamespace()
                    .equals(Ref.MODID)) {

                    ShapedRecipeJsonFactory fac = ShapedRecipeJsonFactory.create(x.getItem(), 1);

                    String[] pattern = x.getRecipePattern();

                    if (x.getEssenceItem() != null) {
                        // if item is special and uses essence to craft
                        for (int i = 0; i < pattern.length; i++) {
                            pattern[i] = pattern[i].replaceAll(" ", "E");
                        }
                    }

                    String all = Strings.join(pattern, "");

                    if (x.getEssenceItem() != null) {
                        fac.input('E', x.getEssenceItem());
                    }
                    if (all.contains("M")) {
                        fac.input('M', x.getMaterial());
                    }
                    if (all.contains("S")) {
                        fac.input('S', Items.STICK);
                    }
                    if (all.contains("B")) {
                        fac.input('B', Items.STRING);
                    }

                    for (String pat : pattern) {
                        try {
                            fac.pattern(pat);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    fac.criterion("player_level", EnchantedItemCriterion.Conditions.any());

                    fac.offerTo(consumer);
                }
            });

    }

    static InventoryChangedCriterion.Conditions conditionsFromItem(ItemConvertible itemConvertible) {
        return conditionsFromItemPredicates(ItemPredicate.Builder.create()
            .item(itemConvertible)
            .build());
    }

    private static InventoryChangedCriterion.Conditions conditionsFromItemPredicates(ItemPredicate... itemPredicates) {
        return new InventoryChangedCriterion.Conditions(EntityPredicate.Extended.EMPTY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, itemPredicates);
    }

}
