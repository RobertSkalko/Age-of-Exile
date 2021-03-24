package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.recipe_types.FoodShapeless;
import com.robertx22.age_of_exile.player_skills.recipe_types.MyShapelessCategory;
import com.robertx22.age_of_exile.player_skills.recipe_types.ReiShapeless;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ReiPlugin implements REIPluginV0 {
    public static final Identifier FOOD = Ref.id("food");

    @Override
    public Identifier getPluginIdentifier() {
        return Ref.id("rei_plugin");
    }

    @Override
    public void registerOthers(RecipeHelper recipeHelper) {

        recipeHelper.registerWorkingStations(FOOD, EntryStack.create(ModRegistry.MISC_ITEMS.SCRIBE_BUFF_BLOCK));

    }

    @Override
    public void registerPluginCategories(RecipeHelper recipeHelper) {
        recipeHelper.registerCategories(
            new MyShapelessCategory(FOOD, ModRegistry.MISC_ITEMS.SCRIBE_BUFF_BLOCK));
    }

    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {
        recipeHelper.registerRecipes(FOOD, FoodShapeless.class, ReiShapeless::new);
    }
}