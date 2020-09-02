package com.robertx22.age_of_exile.database.data.currency.base;

import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.EnchantedItemCriterion;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;

public interface IShapelessRecipe {
    
    ShapelessRecipeJsonFactory getRecipe();

    default CriterionConditions trigger() {
        return EnchantedItemCriterion.Conditions.any();
    }
}



