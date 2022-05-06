package com.robertx22.age_of_exile.saveclasses.gearitem.rework;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public interface StatTooltipInfo {

    public Stat getStat();

    public ModType getType();

    public float getValue();

}
