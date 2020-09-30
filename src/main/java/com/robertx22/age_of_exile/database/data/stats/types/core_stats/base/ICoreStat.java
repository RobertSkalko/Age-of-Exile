package com.robertx22.age_of_exile.database.data.stats.types.core_stats.base;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;

import java.util.List;

public interface ICoreStat extends IGUID {

    void addToOtherStats(EntityCap.UnitData unit, InCalcStatData data);

    List<StatModifier> statsThatBenefit();

}