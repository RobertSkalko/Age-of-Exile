package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;

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

        for (EffectInstance instance : entity.getActiveEffects()) {
            if (instance.getEffect() instanceof IApplyableStats) {
                IApplyableStats stat = (IApplyableStats) instance.getEffect();
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
