package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types.*;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.registry.Registry;

public class ModRecipeSerializers {

    public RecipeSerializer<FoodShapeless> FOOD = register("food_shapeless", new FoodSerializer());
    public RecipeSerializer<TabletShapeless> TABLET = register("tablet_shapeless", new TabletSerializer());
    public RecipeSerializer<AlchemyShapeless> ALCHEMY = register("alchemy_shapeless", new AlchemySerializer());
    public RecipeSerializer<SmithingShapeless> SMITHING = register("smithing_shapeless", new SmithingSerializer());

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, Ref.id(id), serializer);
    }
}
