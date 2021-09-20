package com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types;

import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class TabletSerializer extends StationShapeless.Serializer<TabletShapeless> {

    @Override
    public TabletShapeless createNew(ResourceLocation id, String group, ItemStack output, NonNullList input) {
        return new TabletShapeless(id, group, output, input);
    }


    @Override
    public Class<IRecipeSerializer<?>> getRegistryType() {
        return StationShapeless.Serializer.<IRecipeSerializer<?>>castClass(TabletSerializer.class);
    }


}
