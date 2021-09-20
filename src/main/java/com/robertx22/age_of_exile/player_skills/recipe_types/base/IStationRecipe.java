package com.robertx22.age_of_exile.player_skills.recipe_types.base;

import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.criterion.EnchantedItemTrigger;

public interface IStationRecipe {
    StationShapelessFactory getStationRecipe();

    default ICriterionInstance trigger() {
        return EnchantedItemTrigger.Instance.enchantedItem();
    }
}
