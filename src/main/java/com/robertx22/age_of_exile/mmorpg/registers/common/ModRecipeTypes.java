package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {

    public IRecipeType<StationShapeless> FOOD_RECIPE = register("food_crafting");
    public IRecipeType<StationShapeless> TABLET_RECIPE = register("tablet_crafting");
    public IRecipeType<StationShapeless> ALCHEMY_RECIPE = register("alchemy_crafting");
    public IRecipeType<StationShapeless> SMITHING_RECIPE = register("smithing_crafting");

    static <T extends Recipe<?>> RecipeType<T> register(final String string) {
        return Registry.register(Registry.RECIPE_TYPE, (ResourceLocation) SlashRef.id(string), new RecipeType<T>() {
            public String toString() {
                return string;
            }
        });
    }
}
