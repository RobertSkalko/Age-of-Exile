package com.robertx22.age_of_exile.saveclasses.gearitem.rework;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.wrapped_primitives.RpgLevel;
import com.robertx22.age_of_exile.saveclasses.wrapped_primitives.StatPercent;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class StatModifierInfo implements StatTooltipInfo {

    public final StatModifier mod;
    public final StatPercent percent;
    public final RpgLevel level;

    public final ExactStatData exactStat;

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

    @Override
    public Stat getStat() {
        return mod.GetStat();
    }

    @Override
    public ModType getType() {
        return mod.getModType();
    }

    @Override
    public float getValue() {
        return exactStat.getAverageValue();
    }

}
