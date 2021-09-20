package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ModRecipeTypes {

    public static void init() {

    }

    public static IRecipeType<StationShapeless> FOOD_RECIPE = register("food_crafting");
    public static IRecipeType<StationShapeless> TABLET_RECIPE = register("tablet_crafting");
    public static IRecipeType<StationShapeless> ALCHEMY_RECIPE = register("alchemy_crafting");
    public static IRecipeType<StationShapeless> SMITHING_RECIPE = register("smithing_crafting");

    static <T extends IRecipe<?>> IRecipeType<T> register(final String string) {
        return Registry.register(Registry.RECIPE_TYPE, (ResourceLocation) SlashRef.id(string), new IRecipeType<T>() {
            @Override
            public String toString() {
                return string;
            }
        });
    }
}
