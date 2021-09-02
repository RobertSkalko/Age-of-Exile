package com.robertx22.age_of_exile.aoe_data.datapacks.generators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.IStationRecipe;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
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
import java.util.HashMap;
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

        gearRecipe(consumer, ModRegistry.GEAR_ITEMS.NECKLACES, GearSlots.NECKLACE);
        gearRecipe(consumer, ModRegistry.GEAR_ITEMS.RINGS, GearSlots.RING);
        gearRecipe(consumer, ModRegistry.GEAR_ITEMS.SCEPTERS, GearSlots.SCEPTER);
        gearRecipe(consumer, ModRegistry.GEAR_ITEMS.STAFFS, GearSlots.STAFF);

    }

    public static void gearRecipe(Consumer<RecipeJsonProvider> cons, HashMap<VanillaMaterial, Item> map, String slot) {

        map.entrySet()
            .forEach(x -> {

                ShapedRecipeJsonFactory fac = ShapedRecipeJsonFactory.create(x.getValue(), 1);

                String[] pattern = getRecipePattern(ExileDB.GearSlots()
                    .get(slot));

                String all = Strings.join(pattern, "");

                if (all.contains("M")) {
                    if (x.getKey().mat.tag != null) {
                        fac.input('M', x.getKey().mat.tag);
                    } else {
                        fac.input('M', x.getKey().mat.item);
                    }
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

                fac.offerTo(cons);
            });
    }

    public static String[] getRecipePattern(GearSlot type) {

        String id = type.guid;

        if (id.equals(GearSlots.SWORD)) {
            return new String[]{
                " M ",
                " M ",
                " S "
            };
        }

        if (id.equals(GearSlots.AXE)) {
            return new String[]{
                "MM ",
                " S ",
                " S "
            };
        }

        if (id.equals(GearSlots.SCEPTER)) {
            return new String[]{
                "M  ",
                "MS ",
                "SS "
            };
        }

        if (id.equals(GearSlots.STAFF)) {
            return new String[]{
                "  M",
                "SM ",
                "SS "
            };
        }

        if (id.equals(GearSlots.BOW)) {
            return new String[]{
                " MB",
                "M B",
                " MB"
            };
        }
        if (id.equals(GearSlots.CROSBOW)) {
            return new String[]{
                "MSM",
                "S S",
                " S "
            };
        }

        if (id.equals(GearSlots.CHEST)) {
            return new String[]{
                "M M",
                "MMM",
                "MMM"
            };
        }
        if (id.equals(GearSlots.BOW)) {
            return new String[]{
                "M M",
                "M M"
            };
        }
        if (id.equals(GearSlots.PANTS)) {
            return new String[]{
                "MMM",
                "M M",
                "M M"
            };
        }
        if (id.equals(GearSlots.HELMET)) {
            return new String[]{
                "MMM",
                "M M"
            };
        }

        if (id.equals(GearSlots.NECKLACE)) {
            return new String[]{
                "MMM",
                "M M",
                "MMM"
            };
        }
        if (id.equals(GearSlots.RING)) {
            return new String[]{
                " M ",
                "M M",
                " M "
            };
        }

        System.out.print("NO RECIPE FOR TAG ");

        return null;
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
