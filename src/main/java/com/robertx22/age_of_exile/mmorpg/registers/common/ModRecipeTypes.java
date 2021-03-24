package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipeTypes {

    public RecipeType<CraftingRecipe> FOOD_RECIPE = register("food_crafting");

    static <T extends Recipe<?>> RecipeType<T> register(final String string) {
        return Registry.register(Registry.RECIPE_TYPE, (Identifier) Ref.id(string), new RecipeType<T>() {
            public String toString() {
                return string;
            }
        });
    }
}
