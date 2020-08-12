package com.robertx22.age_of_exile.database.data.stats.types.core_stats.base;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Styles;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCoreStat extends Stat implements ICoreStat {

    @Override
    public StatScaling getScaling() {
        return StatScaling.LINEAR;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.CoreStat;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    public float getPercent(StatData data) {
        return data.getAverageValue() * 100;
    }

    public List<Text> getCoreStatTooltip(EntityCap.UnitData unitdata, StatData data) {

        TooltipInfo info = new TooltipInfo(unitdata, null);

        List<Text> list = new ArrayList<>();
        list.add(Styles.GREENCOMP()
            .append("Stats that benefit: "));
        getMods(data, unitdata.getLevel()).forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;

    }

    public List<ExactStatData> getMods(StatData data, int lvl) {
        int perc = (int) getPercent(data);

        List<ExactStatData> list = new ArrayList<>();
        for (StatModifier x : this.statsThatBenefit()) {
            ExactStatData exactStatData = x.ToExactStat(100, lvl);
            exactStatData.percentIncrease = perc;
            exactStatData.increaseByAddedPercent();
            list.add(exactStatData);
        }
        return list;

    }

    @Override
    public void addToOtherStats(EntityCap.UnitData unitdata, StatData data) {
        getMods(data, unitdata.getLevel()).forEach(x -> x.applyStats(unitdata));
    }

}


