package com.robertx22.mine_and_slash.database.data.currency.base;

import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.EnchantedItemCriterion;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.ItemConvertible;

public interface IShapedRecipe {
    ShapedRecipeJsonFactory getRecipe();

    default ShapedRecipeJsonFactory shaped(ItemConvertible pro) {
        return ShapedRecipeJsonFactory.create(pro, 1);
    }

    default ShapedRecipeJsonFactory shaped(ItemConvertible pro, int i) {
        return ShapedRecipeJsonFactory.create(pro, i);

    }

    default CriterionConditions trigger() {
        return EnchantedItemCriterion.Conditions.any();
    }
}



