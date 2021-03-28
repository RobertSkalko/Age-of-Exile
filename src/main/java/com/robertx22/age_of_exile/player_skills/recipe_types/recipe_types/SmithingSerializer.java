package com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types;

import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class SmithingSerializer extends StationShapeless.Serializer<SmithingShapeless> {

    @Override
    public SmithingShapeless createNew(Identifier id, String group, ItemStack output, DefaultedList input) {
        return new SmithingShapeless(id, group, output, input);
    }

}

