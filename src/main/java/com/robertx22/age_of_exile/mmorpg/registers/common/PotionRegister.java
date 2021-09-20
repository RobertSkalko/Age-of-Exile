package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.player_skills.items.foods.FoodExileEffect;
import com.robertx22.age_of_exile.player_skills.items.protection_tablets.effects.AntiPotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ModStatusEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.FoodExileStatusEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.compat_food_effects.HealthRegenFoodEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.compat_food_effects.ManaRegenFoodEffect;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class PotionRegister {

    public HashMap<FoodExileEffect, Effect> FOOD_EFFECT_MAP = new HashMap<>();

    public static ResourceLocation FOOD_HP = new ResourceLocation(SlashRef.MODID, "food_health_regen");
    public static ResourceLocation FOOD_MANA = new ResourceLocation(SlashRef.MODID, "food_mana_regen");

    public AntiPotionEffect ANTI_WITHER = new AntiPotionEffect(Effects.WITHER);
    public AntiPotionEffect ANTI_POISON = new AntiPotionEffect(Effects.POISON);

    public Effect KNOCKBACK_RESISTANCE = new ModStatusEffect(net.minecraft.potion.EffectType.BENEFICIAL, 1)
        .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "648D7564-6A60-4F59-8ABE-C2C27A6DD7A9", 0.75F, AttributeModifier.Operation.ADDITION);

    public Effect SCROLL_BUFF = new ModStatusEffect(net.minecraft.potion.EffectType.BENEFICIAL, 1);

    HashMap<String, ExileStatusEffect> exileEffectsMap = new HashMap<>();

    public ExileStatusEffect getExileEffect(String num) {
        Preconditions.checkArgument(exileEffectsMap.containsKey(num), num + " effect not there?");
        return exileEffectsMap.get(num);
    }

    public PotionRegister() {

        for (FoodExileEffect exileEffect : FoodExileEffect.values()) {
            FoodExileStatusEffect statusEffect = new FoodExileStatusEffect(exileEffect);
            Registry.register(Registry.MOB_EFFECT, SlashRef.id("foods/" + exileEffect.id), statusEffect);
            FOOD_EFFECT_MAP.put(exileEffect, statusEffect);
        }

        for (int i = 0; i < 20; i++) {
            String key = ExileStatusEffect.getIdPath(EffectType.negative, i);
            ExileStatusEffect eff = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(SlashRef.MODID, key), new ExileStatusEffect(EffectType.negative, i));
            exileEffectsMap.put(key, eff);
        }
        for (int i = 0; i < 40; i++) {
            String key = ExileStatusEffect.getIdPath(EffectType.beneficial, i);
            ExileStatusEffect eff = Registry.register(Registry.MOB_EFFECT, new ResourceLocation(SlashRef.MODID, key), new ExileStatusEffect(EffectType.beneficial, i));
            exileEffectsMap.put(key, eff);
        }

        Registry.register(Registry.MOB_EFFECT, SlashRef.id("knockback_resist"), KNOCKBACK_RESISTANCE);
        Registry.register(Registry.MOB_EFFECT, SlashRef.id("anti_wither"), ANTI_WITHER);
        Registry.register(Registry.MOB_EFFECT, SlashRef.id("anti_poison"), ANTI_POISON);
        Registry.register(Registry.MOB_EFFECT, SlashRef.id("scroll_buff"), SCROLL_BUFF);

        Registry.register(Registry.MOB_EFFECT, FOOD_HP, HealthRegenFoodEffect.INSTANCE);
        Registry.register(Registry.MOB_EFFECT, FOOD_MANA, ManaRegenFoodEffect.INSTANCE);

    }

}
