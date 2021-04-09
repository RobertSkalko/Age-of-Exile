package com.robertx22.age_of_exile.aoe_data.database.sets;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class GearSetsAdder implements ISlashRegistryInit {

    public static String BONE_SET = "bone_set";

    @Override
    public void registerAll() {

        GearSet.Builder.of(BONE_SET)
            .stat(4, new OptScaleExactStat(25, SpecialStats.RANGED_CRIT_DMG_AGAINST_LIVING))
            .build();

    }
}
