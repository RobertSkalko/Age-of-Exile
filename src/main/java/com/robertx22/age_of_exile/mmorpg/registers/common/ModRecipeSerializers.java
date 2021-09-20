package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types.*;
import net.minecraft.item.crafting.IRecipeSerializer;

public class ModRecipeSerializers {
    public static void init() {

    }

    public RegObj<IRecipeSerializer<FoodShapeless>> FOOD = Def.recipeSer("food_shapeless", new FoodSerializer());
    public RegObj<IRecipeSerializer<TabletShapeless>> TABLET = Def.recipeSer("tablet_shapeless", new TabletSerializer());
    public RegObj<IRecipeSerializer<AlchemyShapeless>> ALCHEMY = Def.recipeSer("alchemy_shapeless", new AlchemySerializer());
    public RegObj<IRecipeSerializer<SmithingShapeless>> SMITHING = Def.recipeSer("smithing_shapeless", new SmithingSerializer());

}
