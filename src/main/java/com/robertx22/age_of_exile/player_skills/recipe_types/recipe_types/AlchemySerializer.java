package com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types;

import com.robertx22.age_of_exile.player_skills.recipe_types.base.StationShapeless;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class AlchemySerializer extends StationShapeless.Serializer<AlchemyShapeless> {

    @Override
    public AlchemyShapeless createNew(Identifier id, String group, ItemStack output, DefaultedList input) {
        return new AlchemyShapeless(id, group, output, input);
    }

}

