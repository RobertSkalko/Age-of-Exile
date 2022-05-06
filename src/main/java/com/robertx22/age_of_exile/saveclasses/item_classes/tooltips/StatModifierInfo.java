package com.robertx22.age_of_exile.saveclasses.item_classes.tooltips;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.saveclasses.wrapped_primitives.RpgLevel;

public class StatModifierInfo {

    public StatModifier mod;
    public int percent;
    public RpgLevel level;

    public StatModifierInfo(StatModifier mod, int percent, RpgLevel level) {
        this.mod = mod;
        this.percent = percent;
        this.level = level;
    }
}
