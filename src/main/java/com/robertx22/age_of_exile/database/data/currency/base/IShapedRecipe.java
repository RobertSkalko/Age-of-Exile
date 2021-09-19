package com.robertx22.age_of_exile.database.data.currency.base;

import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.criterion.EnchantedItemTrigger;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.util.IItemProvider;

public interface IShapedRecipe {
    ShapedRecipeBuilder getRecipe();

    default ShapedRecipeBuilder shaped(IItemProvider pro) {
        return ShapedRecipeBuilder.shaped(pro, 1);
    }

    default ShapedRecipeBuilder shaped(IItemProvider pro, int i) {
        return ShapedRecipeBuilder.shaped(pro, i);
    }

    default ICriterionInstance trigger() {
        return EnchantedItemTrigger.Instance.enchantedItem();
    }
}



