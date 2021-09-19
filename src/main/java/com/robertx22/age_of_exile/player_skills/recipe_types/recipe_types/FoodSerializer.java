package com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types;

import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.core.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FoodSerializer extends StationShapeless.Serializer<FoodShapeless> {

    @Override
    public FoodShapeless createNew(ResourceLocation id, String group, ItemStack output, NonNullList input) {
        return new FoodShapeless(id, group, output, input);
    }

}
