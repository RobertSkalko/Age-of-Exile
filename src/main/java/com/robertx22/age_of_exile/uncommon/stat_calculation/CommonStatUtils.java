package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class CommonStatUtils {

    public static List<StatContext> addExactCustomStats(LivingEntity en) {
        return Load.Unit(en)
            .getCustomExactStats()
            .getStatAndContext(en);
    }

    public static List<StatContext> addPotionStats(LivingEntity entity) {

        List<StatContext> list = new ArrayList<>();

        for (StatusEffectInstance instance : entity.getStatusEffects()) {
            if (instance.getEffectType() instanceof IApplyableStats) {
                IApplyableStats stat = (IApplyableStats) instance.getEffectType();
                try {
                    list.addAll(stat.getStatAndContext(entity));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

}
