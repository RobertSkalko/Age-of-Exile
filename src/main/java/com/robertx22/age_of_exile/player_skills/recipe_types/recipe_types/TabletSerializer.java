package com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types;

import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.core.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TabletSerializer extends StationShapeless.Serializer<TabletShapeless> {

    @Override
    public TabletShapeless createNew(ResourceLocation id, String group, ItemStack output, NonNullList input) {
        return new TabletShapeless(id, group, output, input);
    }

}
