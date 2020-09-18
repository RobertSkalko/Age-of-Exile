package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class ExileEffectsManager {
    public static void apply(ExileEffect reg, LivingEntity caster, LivingEntity target, int duration) {

        if (caster.world.isClient) {
            return;
        }
        ExileStatusEffect effect = reg.getStatusEffect();

        StatusEffectInstance instance = target.getStatusEffect(effect);
        ExileEffectInstanceData extraData;

        if (instance != null) {
            extraData = Load.Unit(target)
                .getStatusEffectsData()
                .get(effect);
            if (extraData == null) {
                extraData = new ExileEffectInstanceData();
            }
        } else {
            extraData = new ExileEffectInstanceData();
        }

        StatusEffectInstance newInstance = new StatusEffectInstance(effect, duration, 1, false, false, true);

        target.addStatusEffect(newInstance);

        Load.Unit(target)
            .getStatusEffectsData()
            .set(effect, extraData);

        Load.Unit(target)
            .setEquipsChanged(true);

    }
}
