package com.robertx22.age_of_exile.database.data.food_effects;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import net.minecraft.item.Item;

public class FoodEffectUtils {

    public static boolean isFood(Item item) {

        return item.getFoodComponent() != null;
    }

    public static FoodEffect getEffect(Item item) {

        if (ModConfig.get().foodEffects.ENABLE_FOOD_EFFECTS) {

            if (ModConfig.get().foodEffects.ENABLE_AUTO_FOOD_COMPATIBILITY) {
                return FoodEffectBuilder.auto(item); // TODO
            }

        }
        return null;
    }

}
