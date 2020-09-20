package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileStatusEffect;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.food_effects.HealthRegenFoodEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.food_effects.MagicShieldFoodEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.food_effects.ManaRegenFoodEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class PotionRegister {

    public static Identifier FOOD_HP = new Identifier(Ref.MODID, "food_health_regen");
    public static Identifier FOOD_MANA = new Identifier(Ref.MODID, "food_mana_regen");
    public static Identifier FOOD_MAGIC_REGEN = new Identifier(Ref.MODID, "food_magic_shield_regen");

    HashMap<String, ExileStatusEffect> exileEffectsMap = new HashMap<>();

    public ExileStatusEffect getExileEffect(String num) {
        return exileEffectsMap.get(num);
    }

    public PotionRegister() {

        if (MMORPG.RUN_DEV_TOOLS) { // TODO
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
        }

        Registry.register(Registry.STATUS_EFFECT, FOOD_HP, HealthRegenFoodEffect.INSTANCE);
        Registry.register(Registry.STATUS_EFFECT, FOOD_MANA, ManaRegenFoodEffect.INSTANCE);
        Registry.register(Registry.STATUS_EFFECT, FOOD_MAGIC_REGEN, MagicShieldFoodEffect.INSTANCE);

    }

}
