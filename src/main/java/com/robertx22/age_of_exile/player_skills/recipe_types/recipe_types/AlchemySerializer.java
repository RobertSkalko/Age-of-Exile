package com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types;

import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.core.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AlchemySerializer extends StationShapeless.Serializer<AlchemyShapeless> {

    @Override
    public AlchemyShapeless createNew(ResourceLocation id, String group, ItemStack output, NonNullList input) {
        return new AlchemyShapeless(id, group, output, input);
    }

}

