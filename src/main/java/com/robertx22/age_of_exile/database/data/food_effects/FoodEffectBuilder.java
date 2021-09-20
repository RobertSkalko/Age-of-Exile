package com.robertx22.age_of_exile.database.data.food_effects;

import com.mojang.datafixers.util.Pair;
import com.robertx22.age_of_exile.mixins.StatusEffectAccessor;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashPotions;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class FoodEffectBuilder {

    public static FoodEffect auto(Item food) {

        if (food.getFoodProperties() == null) {
            return null;
        }
        if (food == Items.ROTTEN_FLESH || food == Items.SPIDER_EYE) {
            return null;
        }

        int durationSeconds = 30;

        FoodEffect data = new FoodEffect();

        Food foodcomponent = food.getFoodProperties();

        float total = foodcomponent.getNutrition() + foodcomponent.getSaturationModifier();

        total = MathHelper.clamp(total, 1, 30);

        total *= durationSeconds;

        total /= 12F;

        float effectMod = 1;

        for (Pair<EffectInstance, Float> x : foodcomponent.getEffects()) {
            Effect efg = x.getFirst()
                .getEffect();
            StatusEffectAccessor acc = (StatusEffectAccessor) efg;

            if (acc.getCategory() == EffectType.BENEFICIAL) {
                effectMod += 0.15F;
            } else if (acc.getCategory() == EffectType.HARMFUL) {
                effectMod -= 0.75F;
            }
        }
        if (effectMod < 0) {
            effectMod = 0;
        }
        float value = total * effectMod;

        ResourceLocation effect = null;

        if (foodcomponent.isMeat()) {
            effect = SlashPotions.FOOD_HP;
            value *= 0.75F;
        } else {
            effect = SlashPotions.FOOD_MANA;
            value *= 1;
        }

        data.effects_given.add(new StatusEffectData(effect, durationSeconds, (int) value));

        return data;

    }

}
