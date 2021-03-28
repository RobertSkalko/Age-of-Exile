package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.recipe_types.MyShapelessCategory;
import com.robertx22.age_of_exile.player_skills.recipe_types.ReiShapelessDisplay;
import com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types.FoodShapeless;
import com.robertx22.age_of_exile.player_skills.recipe_types.recipe_types.TabletShapeless;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.minecraft.util.Identifier;

public class ReiPlugin implements REIPluginV0 {

    public static final Identifier FOOD = Ref.id("food");
    public static final Identifier TABLET = Ref.id("tablet");

    @Override
    public Identifier getPluginIdentifier() {
        return Ref.id("rei_plugin");
    }

    @Override
    public void registerOthers(RecipeHelper recipeHelper) {

        recipeHelper.registerWorkingStations(FOOD, EntryStack.create(ModRegistry.MISC_ITEMS.COOKING_STATION));
        recipeHelper.registerWorkingStations(TABLET, EntryStack.create(ModRegistry.MISC_ITEMS.TABLET_STATION));

    }

    @Override
    public void registerPluginCategories(RecipeHelper recipeHelper) {
        recipeHelper.registerCategories(new MyShapelessCategory(FOOD, ModRegistry.MISC_ITEMS.COOKING_STATION));
        recipeHelper.registerCategories(new MyShapelessCategory(TABLET, ModRegistry.MISC_ITEMS.TABLET_STATION));
    }

    @Override
    public void registerRecipeDisplays(RecipeHelper recipeHelper) {

        recipeHelper.registerRecipes(FOOD, FoodShapeless.class, ReiShapelessDisplay::new);
        recipeHelper.registerRecipes(FOOD, TabletShapeless.class, ReiShapelessDisplay::new);

    }
}