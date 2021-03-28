package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipeTypes {

    public RecipeType<StationShapeless> FOOD_RECIPE = register("food_crafting");
    public RecipeType<StationShapeless> TABLET_RECIPE = register("tablet_crafting");

    static <T extends Recipe<?>> RecipeType<T> register(final String string) {
        return Registry.register(Registry.RECIPE_TYPE, (Identifier) Ref.id(string), new RecipeType<T>() {
            public String toString() {
                return string;
            }
        });
    }
}
