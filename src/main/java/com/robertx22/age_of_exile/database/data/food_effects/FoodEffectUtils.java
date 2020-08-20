package com.robertx22.age_of_exile.database.data.food_effects;

import net.minecraft.item.Item;

public class FoodEffectUtils {

    public static boolean isFood(Item item) {
        return item.getFoodComponent() != null;
    }

    public static FoodEffect getEffect(Item item) {
        return FoodEffectBuilder.auto(item); // TODO
    }

}
