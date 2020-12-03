package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public interface IApplyableStats {

    List<StatContext> getStatAndContext(LivingEntity en);

}
