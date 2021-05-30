package com.robertx22.age_of_exile.database.data.food_effects;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.player_skills.items.alchemy.AlchemyPotionItem;
import net.minecraft.item.Item;

public class FoodEffectUtils {

    public static boolean isFood(Item item) {

        return getEffect(item) != null;
    }

    public static FoodEffect getEffect(Item item) {

        if (ModConfig.get().foodEffects.ENABLE_FOOD_EFFECTS) {

            if (item instanceof AlchemyPotionItem) {
                AlchemyPotionItem cast = (AlchemyPotionItem) item;
                return cast.getFoodEffect();
            }

            if (ModConfig.get().foodEffects.ENABLE_AUTO_FOOD_COMPAT) {
                return FoodEffectBuilder.auto(item); // TODO
            }

        }
        return null;
    }

}
