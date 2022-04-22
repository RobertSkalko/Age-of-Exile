package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.ModStatusEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.compat_food_effects.HealthRegenFoodEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.compat_food_effects.ManaRegenFoodEffect;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class SlashPotions {

    public static ResourceLocation FOOD_HP = new ResourceLocation(SlashRef.MODID, "food_health_regen");
    public static ResourceLocation FOOD_MANA = new ResourceLocation(SlashRef.MODID, "food_mana_regen");

    public static RegObj<Effect> KNOCKBACK_RESISTANCE = Def.potion("knockback_resist", () -> new ModStatusEffect(net.minecraft.potion.EffectType.BENEFICIAL, 1)
        .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "648D7564-6A60-4F59-8ABE-C2C27A6DD7A9", 0.75F, AttributeModifier.Operation.ADDITION));

    static HashMap<String, RegObj<ExileStatusEffect>> exileEffectsMap = new HashMap<>();

    public static ExileStatusEffect getExileEffect(String num) {
        Preconditions.checkArgument(exileEffectsMap.containsKey(num), num + " effect not there?");
        return exileEffectsMap.get(num)
            .get();
    }

    public static void init() {

        for (int i = 0; i < 20; i++) {
            String key = ExileStatusEffect.getIdPath(EffectType.negative, i);
            ExileStatusEffect eff = new ExileStatusEffect(EffectType.negative, i);

            exileEffectsMap.put(key, Def.potion(key, () -> eff));
        }
        for (int i = 0; i < 40; i++) {
            String key = ExileStatusEffect.getIdPath(EffectType.beneficial, i);
            ExileStatusEffect eff = new ExileStatusEffect(EffectType.beneficial, i);

            exileEffectsMap.put(key, Def.potion(key, () -> eff));
        }

        Def.potion(FOOD_HP.getPath(), () -> HealthRegenFoodEffect.INSTANCE);
        Def.potion(FOOD_MANA.getPath(), () -> ManaRegenFoodEffect.INSTANCE);

    }

}
