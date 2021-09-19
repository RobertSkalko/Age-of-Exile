package com.robertx22.age_of_exile.database.data.currency.base;

import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.criterion.EnchantedItemTrigger;
import net.minecraft.data.ShapelessRecipeBuilder;

public interface IShapelessRecipe {

    ShapelessRecipeBuilder getRecipe();

    default ICriterionInstance trigger() {
        return EnchantedItemTrigger.Instance.enchantedItem();
    }
}



