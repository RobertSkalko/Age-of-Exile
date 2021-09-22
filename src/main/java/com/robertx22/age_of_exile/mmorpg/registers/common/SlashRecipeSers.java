package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.player_skills.recipe_types.ProfCraftingTabletRecipe;
import com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;

public class SlashRecipeSers {
    public static void init() {

    }

    public static RegObj<SpecialRecipeSerializer<ProfCraftingTabletRecipe>> PROF_CRAFTING = Def.recipeSer("prof_crafting", () -> new SpecialRecipeSerializer<>(ProfCraftingTabletRecipe::new));

    public static RegObj<IRecipeSerializer<FoodShapeless>> FOOD = Def.recipeSer("food_shapeless", () -> new FoodSerializer());
    public static RegObj<IRecipeSerializer<TabletShapeless>> TABLET = Def.recipeSer("tablet_shapeless", () -> new TabletSerializer());
    public static RegObj<IRecipeSerializer<AlchemyShapeless>> ALCHEMY = Def.recipeSer("alchemy_shapeless", () -> new AlchemySerializer());
    public static RegObj<IRecipeSerializer<SmithingShapeless>> SMITHING = Def.recipeSer("smithing_shapeless", () -> new SmithingSerializer());

}
