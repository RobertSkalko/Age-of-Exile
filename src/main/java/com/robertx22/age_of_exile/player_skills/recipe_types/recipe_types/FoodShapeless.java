package com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.core.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class FoodShapeless extends StationShapeless {

    public FoodShapeless(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> input) {
        super(id, group, output, input);
    }

    @Override
    public RecipeType<?> getType() {
        return ModRegistry.RECIPE_TYPES.FOOD_RECIPE;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRegistry.RECIPE_SER.FOOD;
    }

}
