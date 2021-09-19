package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;

public interface IAfterStatCalc {

    void affectUnit(EntityData unitdata, StatData statdata);
}
