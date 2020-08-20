package com.robertx22.age_of_exile.database.data.food_effects;

import com.robertx22.age_of_exile.mmorpg.registers.common.PotionRegister;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;

public class FoodEffectBuilder {

    public static FoodEffect auto(Item food) {

        if (food.getFoodComponent() == null) {
            return null;
        }

        int durationSeconds = 30;

        FoodEffect data = new FoodEffect();

        FoodComponent foodcomponent = food.getFoodComponent();

        float total = foodcomponent.getHunger() + foodcomponent.getSaturationModifier();
        float value = total * 1.8F;

        Identifier effect = null;

        if (ItemTags.FISHES.contains(food)) {
            effect = PotionRegister.FOOD_MAGIC_REGEN;
        } else if (foodcomponent.isMeat()) {
            effect = PotionRegister.FOOD_HP;
        } else {
            effect = PotionRegister.FOOD_MANA;
            value *= 1.75F;
        }

        data.effects_given.add(new StatusEffectData(effect, durationSeconds, (int) value));

        return data;

    }

}
