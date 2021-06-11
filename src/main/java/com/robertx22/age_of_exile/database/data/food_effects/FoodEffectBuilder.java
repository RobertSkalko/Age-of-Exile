package com.robertx22.age_of_exile.database.data.food_effects;

import com.mojang.datafixers.util.Pair;
import com.robertx22.age_of_exile.mixins.StatusEffectAccessor;
import com.robertx22.age_of_exile.mmorpg.registers.common.PotionRegister;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class FoodEffectBuilder {

    public static FoodEffect auto(Item food) {

        if (food.getFoodComponent() == null) {
            return null;
        }
        if (food == Items.ROTTEN_FLESH || food == Items.SPIDER_EYE) {
            return null;
        }

        int durationSeconds = 30;

        FoodEffect data = new FoodEffect();

        FoodComponent foodcomponent = food.getFoodComponent();

        float total = foodcomponent.getHunger() + foodcomponent.getSaturationModifier();

        total = MathHelper.clamp(total, 1, 30);

        total *= durationSeconds;

        total /= 12F;

        float effectMod = 1;

        for (Pair<StatusEffectInstance, Float> x : foodcomponent.getStatusEffects()) {
            StatusEffect efg = x.getFirst()
                .getEffectType();
            StatusEffectAccessor acc = (StatusEffectAccessor) efg;

            if (acc.getType() == StatusEffectType.BENEFICIAL) {
                effectMod += 0.15F;
            } else if (acc.getType() == StatusEffectType.HARMFUL) {
                effectMod -= 0.75F;
            }
        }
        if (effectMod < 0) {
            effectMod = 0;
        }
        float value = total * effectMod;

        Identifier effect = null;

        if (foodcomponent.isMeat()) {
            effect = PotionRegister.FOOD_HP;
            value *= 0.75F;
        } else {
            effect = PotionRegister.FOOD_MANA;
            value *= 1;
        }

        data.effects_given.add(new StatusEffectData(effect, durationSeconds, (int) value));

        return data;

    }

}
