package com.robertx22.age_of_exile.saveclasses.gearitem.rework;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.wrapped_primitives.RpgLevel;
import com.robertx22.age_of_exile.saveclasses.wrapped_primitives.StatPercent;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class StatModifierInfo {
    // todo allow combining?

    public StatModifier mod;
    public StatPercent percent;
    public RpgLevel level;

    public ExactStatData exactStat;

    public StatModifierInfo(StatModifier mod, StatPercent percent, RpgLevel level) {
        this.mod = mod;
        this.percent = percent;
        this.level = level;

        this.exactStat = mod.ToExactStat(percent.get(), level.get());
    }

    public List<ITextComponent> GetTooltipString(TooltipInfo info) {
        return exactStat.getStat()
            .getTooltipList(this);
    }

}
