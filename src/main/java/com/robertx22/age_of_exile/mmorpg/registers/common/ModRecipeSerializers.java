package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.item.crafting.Recipe;

public class ModRecipeSerializers {

    public IRecipeSerializer<FoodShapeless> FOOD = register("food_shapeless", new FoodSerializer());
    public IRecipeSerializer<TabletShapeless> TABLET = register("tablet_shapeless", new TabletSerializer());
    public IRecipeSerializer<AlchemyShapeless> ALCHEMY = register("alchemy_shapeless", new AlchemySerializer());
    public IRecipeSerializer<SmithingShapeless> SMITHING = register("smithing_shapeless", new SmithingSerializer());

    static <S extends IRecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, Ref.id(id), serializer);
    }
}
