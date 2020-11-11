package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileStatusEffect;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.items.foods.FoodExileEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.FoodExileStatusEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ModStatusEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.compat_food_effects.HealthRegenFoodEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.compat_food_effects.MagicShieldFoodEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.compat_food_effects.ManaRegenFoodEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class PotionRegister {

    public HashMap<FoodExileEffect, StatusEffect> FOOD_EFFECT_MAP = new HashMap<>();

    public static Identifier FOOD_HP = new Identifier(Ref.MODID, "food_health_regen");
    public static Identifier FOOD_MANA = new Identifier(Ref.MODID, "food_mana_regen");
    public static Identifier FOOD_MAGIC_REGEN = new Identifier(Ref.MODID, "food_magic_shield_regen");

    public static StatusEffect KNOCKBACK_RESISTANCE = new ModStatusEffect(StatusEffectType.BENEFICIAL, 1)
        .addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, "648D7564-6A60-4F59-8ABE-C2C27A6DD7A9", 50, EntityAttributeModifier.Operation.ADDITION);

    HashMap<String, ExileStatusEffect> exileEffectsMap = new HashMap<>();

    public ExileStatusEffect getExileEffect(String num) {
        Preconditions.checkArgument(exileEffectsMap.containsKey(num), num + " effect not there?");
        return exileEffectsMap.get(num);
    }

    public PotionRegister() {

        for (FoodExileEffect exileEffect : FoodExileEffect.values()) {
            FoodExileStatusEffect statusEffect = new FoodExileStatusEffect(exileEffect);
            Registry.register(Registry.STATUS_EFFECT, Ref.id("foods/" + exileEffect.id), statusEffect);
            FOOD_EFFECT_MAP.put(exileEffect, statusEffect);
        }

        for (int i = 0; i < 20; i++) {
            String key = ExileStatusEffect.getIdPath(StatusEffectType.NEUTRAL, i);
            ExileStatusEffect eff = Registry.register(Registry.STATUS_EFFECT, new Identifier(Ref.MODID, key), new ExileStatusEffect(StatusEffectType.NEUTRAL, i));
            exileEffectsMap.put(key, eff);
        }
        for (int i = 0; i < 20; i++) {
            String key = ExileStatusEffect.getIdPath(StatusEffectType.HARMFUL, i);

            ExileStatusEffect eff = Registry.register(Registry.STATUS_EFFECT, new Identifier(Ref.MODID, key), new ExileStatusEffect(StatusEffectType.HARMFUL, i));
            exileEffectsMap.put(key, eff);
        }
        for (int i = 0; i < 20; i++) {
            String key = ExileStatusEffect.getIdPath(StatusEffectType.BENEFICIAL, i);

            ExileStatusEffect eff = Registry.register(Registry.STATUS_EFFECT, new Identifier(Ref.MODID, key), new ExileStatusEffect(StatusEffectType.BENEFICIAL, i));
            exileEffectsMap.put(key, eff);
        }

        Registry.register(Registry.STATUS_EFFECT, Ref.id("knockback_resist"), KNOCKBACK_RESISTANCE);

        Registry.register(Registry.STATUS_EFFECT, FOOD_HP, HealthRegenFoodEffect.INSTANCE);
        Registry.register(Registry.STATUS_EFFECT, FOOD_MANA, ManaRegenFoodEffect.INSTANCE);
        Registry.register(Registry.STATUS_EFFECT, FOOD_MAGIC_REGEN, MagicShieldFoodEffect.INSTANCE);

    }

}
