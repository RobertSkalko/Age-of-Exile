package com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types;

import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class SmithingSerializer extends StationShapeless.Serializer<SmithingShapeless> {

    @Override
    public SmithingShapeless createNew(ResourceLocation id, String group, ItemStack output, NonNullList input) {
        return new SmithingShapeless(id, group, output, input);
    }

    @Override
    public Class<IRecipeSerializer<?>> getRegistryType() {
        return StationShapeless.Serializer.<IRecipeSerializer<?>>castClass(SmithingSerializer.class);
    }

}

