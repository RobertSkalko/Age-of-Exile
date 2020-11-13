package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.IApplyStatPotion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class CommonStatUtils {

    public static void addExactCustomStats(UnitData data) {
        data.getCustomExactStats()
            .applyStats(data);
    }

    public static void addPotionStats(LivingEntity entity) {

        for (StatusEffectInstance instance : entity.getStatusEffects()) {
            if (instance.getEffectType() instanceof IApplyStatPotion) {
                IApplyStatPotion stat = (IApplyStatPotion) instance.getEffectType();
                try {
                    stat.applyStats(entity.world, instance, entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void CalcTraitsAndCoreStats(UnitData unit) {

        Unit theunit = unit.getUnit();

    }

}
