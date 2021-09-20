package com.robertx22.age_of_exile.aoe_data.datapacks.generators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ProfessionItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.IStationRecipe;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import joptsimple.internal.Strings;
import net.minecraft.advancements.criterion.*;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.loading.FMLLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.function.Consumer;

public class RecipeGenerator {

    public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting()
        .create();
    protected DirectoryCache cache;

    public RecipeGenerator() {

        try {
            cache = new DirectoryCache(FMLLoader.getGamePath()
                , "datagencache");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Path getBasePath() {
        return FMLLoader.getGamePath();
    }

    protected Path movePath(Path target) {
        String movedpath = target.toString();
        movedpath = movedpath.replace("run", "src/generated/resources");
        return Paths.get(movedpath);
    }

    private Path resolve(Path path, String id) {

        return path.resolve(
            "data/" + SlashRef.MODID + "/recipes/" + id
                + ".json");
    }

    public void run() {
        generateAll(cache);
    }

    protected void generateAll(DirectoryCache cache) {

        Path path = getBasePath();

        generate(x -> {

            Path target = movePath(resolve(path, x.getId()
                .getPath()));

            try {
                IDataProvider.save(GSON, cache, x.serializeRecipe(), target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

    private void generate(Consumer<IFinishedRecipe> consumer) {
        for (Item item : Registry.ITEM) {
            if (item instanceof IShapedRecipe) {
                IShapedRecipe ir = (IShapedRecipe) item;
                ShapedRecipeBuilder rec = ir.getRecipe();
                if (rec != null) {
                    rec.save(consumer);
                }

            }
            if (item instanceof IShapelessRecipe) {
                IShapelessRecipe sr = (IShapelessRecipe) item;
                ShapelessRecipeBuilder srec = sr.getRecipe();
                if (srec != null) {
                    try {
                        srec.save(consumer);
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

        ProfessionItems.SMELTED_ESSENCE.values()
            .forEach(x -> {
                Item ess = ProfessionItems.SALVAGED_ESSENCE_MAP.get(x.get().tier)
                    .get();
                CookingRecipeBuilder.smelting(Ingredient.of(ess), x.get(), 0.2F, 200)
                    .unlockedBy("ess" + x.get().tier, conditionsFromItem(ess))
                    .save(consumer);
            });

        ProfessionItems.SALVAGED_ESSENCE_MAP.values()
            .forEach(x -> {
                if (x.get().tier.lowerTier() != null) {
                    ShapelessRecipeBuilder fac = ShapelessRecipeBuilder.shapeless(x.get(), 1);
                    fac.requires(ProfessionItems.SALVAGED_ESSENCE_MAP.get(x.get().tier.lowerTier())
                        .get(), 4);
                    fac.unlockedBy("player_level", EnchantedItemTrigger.Instance.enchantedItem())
                        .save(consumer);
                }
            });

        gearRecipe(consumer, SlashItems.GearItems.NECKLACES, GearSlots.NECKLACE);
        gearRecipe(consumer, SlashItems.GearItems.RINGS, GearSlots.RING);
        gearRecipe(consumer, SlashItems.GearItems.SCEPTERS, GearSlots.SCEPTER);
        gearRecipe(consumer, SlashItems.GearItems.STAFFS, GearSlots.STAFF);

    }

    public static void gearRecipe(Consumer<IFinishedRecipe> cons, HashMap<VanillaMaterial, RegObj<Item>> map, String slot) {

        map.entrySet()
            .forEach(x -> {

                ShapedRecipeBuilder fac = ShapedRecipeBuilder.shaped(x.getValue()
                    .get(), 1);

                String[] pattern = getRecipePattern(ExileDB.GearSlots()
                    .get(slot));

                String all = Strings.join(pattern, "");

                if (all.contains("M")) {
                    if (x.getKey().mat.tag != null) {
                        fac.define('M', x.getKey().mat.tag);
                    } else {
                        fac.define('M', x.getKey().mat.item);
                    }
                }
                if (all.contains("S")) {
                    fac.define('S', Items.STICK);
                }
                if (all.contains("B")) {
                    fac.define('B', Items.STRING);
                }

                for (String pat : pattern) {
                    try {
                        fac.pattern(pat);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                fac.unlockedBy("player_level", EnchantedItemTrigger.Instance.enchantedItem());

                fac.save(cons);
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

    static InventoryChangeTrigger.Instance conditionsFromItem(IItemProvider itemConvertible) {
        return conditionsFromItemPredicates(ItemPredicate.Builder.item()
            .of(itemConvertible)
            .build());
    }

    private static InventoryChangeTrigger.Instance conditionsFromItemPredicates(ItemPredicate... itemPredicates) {
        return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, itemPredicates);
    }

}
