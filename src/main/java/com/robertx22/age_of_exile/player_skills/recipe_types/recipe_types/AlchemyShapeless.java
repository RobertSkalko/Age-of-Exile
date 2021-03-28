package com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class AlchemyShapeless extends StationShapeless {

    public AlchemyShapeless(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input) {
        super(id, group, output, input);
    }

    @Override
    public RecipeType<?> getType() {
        return ModRegistry.RECIPE_TYPES.ALCHEMY_RECIPE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRegistry.RECIPE_SER.ALCHEMY;
    }

}
