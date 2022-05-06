package com.robertx22.age_of_exile.saveclasses.gearitem.rework;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public class ExactStatInfo implements StatTooltipInfo {
    public final ExactStatData exactStat;

    public ExactStatInfo(ExactStatData exactStat) {
        this.exactStat = exactStat;
    }

    public List<ITextComponent> GetTooltipString(TooltipInfo info) {
        return exactStat.getStat()
            .getTooltipList(this);
    }

    @Override
    public Stat getStat() {
        return exactStat.getStat();
    }

    @Override
    public ModType getType() {
        return exactStat.getType();
    }

    @Override
    public float getValue() {
        return exactStat.getAverageValue();
    }
}
