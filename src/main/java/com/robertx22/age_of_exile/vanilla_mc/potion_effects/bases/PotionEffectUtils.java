package com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data.ExtraPotionData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PotionEffectUtils {

    public static void reApplyToSelf(BasePotionEffect effect, LivingEntity caster) {
        apply(effect, caster, caster);
    }

    public static void applyToSelf(BasePotionEffect effect, LivingEntity caster) {
        apply(effect, caster, caster);
    }

    public static void apply(BasePotionEffect effect, LivingEntity caster, LivingEntity target) {

        if (caster.world.isClient) {
            return;
        }

        int duration = effect.getDurationInTicks(caster);

        EntityCap.UnitData casterData = Load.Unit(caster);

        StatusEffectInstance instance = target.getStatusEffect(effect);
        ExtraPotionData extraData;

        if (instance != null) {
            extraData = PotionDataSaving.getData(target, instance);
        } else {
            extraData = new ExtraPotionData();
        }

        if (extraData == null) {
            extraData = new ExtraPotionData();
        }

        if (extraData.getInitialDurationTicks() > 0) {
            duration = extraData.getInitialDurationTicks(); // if reapplied, apply existing duration
        }

        StatusEffectInstance newInstance = new StatusEffectInstance(effect, duration, extraData.getStacks(), false, false, true);

        // so it can recalc stats cus onpotion remoove/add
        if (instance == null) {

            extraData.casterID = caster.getUuid()
                .toString();
            extraData.setInitialDurationTicks(duration);

        } else {

            if (instance.getDuration() > duration) {
                duration = instance.getDuration();
            }

            extraData.casterID = caster.getUuid()
                .toString();
            extraData.setInitialDurationTicks(duration);
            extraData.addStacks(1, effect);

            target.removeStatusEffect(effect); // HAVE TO REMOVE OR IT WONT ACTUALLY ADD CORRECTLY

        }

        PotionDataSaving.saveData(target, newInstance, extraData);
        target.addStatusEffect(newInstance);

    }

    public static boolean reduceStacks(LivingEntity target, BasePotionEffect effect) {
        return reduceStacks(target, effect, 1);
    }

    public static boolean has(LivingEntity entity, BasePotionEffect effect) {
        return entity.getStatusEffect(effect) != null;
    }

    public static boolean reduceStacks(LivingEntity target, BasePotionEffect effect, int num) {

        StatusEffectInstance instance = target.getStatusEffect(effect);

        if (instance != null) {
            ExtraPotionData extraData = PotionDataSaving.getData(target, instance);

            extraData.decreaseStacks(num, effect);

            if (extraData.getStacks() <= 0) {
                target.removeStatusEffect(effect);
            } else {
                PotionDataSaving.saveData(target, instance, extraData);
            }
            return true;
        }

        return false;
    }

    public static int getStacks(LivingEntity en, BasePotionEffect effect) {
        StatusEffectInstance instance = en.getStatusEffect(effect);

        if (instance != null) {
            ExtraPotionData extraData = PotionDataSaving.getData(en, instance);

            if (extraData != null) {
                return extraData.getStacks();
            }

        }
        return 0;

    }
}
