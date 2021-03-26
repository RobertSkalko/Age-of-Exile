package com.robertx22.age_of_exile.player_skills.recipe_types.base;

import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.EnchantedItemCriterion;

public interface IStationRecipe {
    StationShapelessFactory getStationRecipe();

    default CriterionConditions trigger() {
        return EnchantedItemCriterion.Conditions.any();
    }
}
