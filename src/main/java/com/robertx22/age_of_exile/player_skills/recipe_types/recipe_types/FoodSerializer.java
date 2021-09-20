package com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types;

import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class FoodSerializer extends StationShapeless.Serializer<FoodShapeless> {

    @Override
    public FoodShapeless createNew(ResourceLocation id, String group, ItemStack output, NonNullList input) {
        return new FoodShapeless(id, group, output, input);
    }

    @Override
    public Class<IRecipeSerializer<?>> getRegistryType() {
        return StationShapeless.Serializer.<IRecipeSerializer<?>>castClass(FoodSerializer.class);
    }
}
