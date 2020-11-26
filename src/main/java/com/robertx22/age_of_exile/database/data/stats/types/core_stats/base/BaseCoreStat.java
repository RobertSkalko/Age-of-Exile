package com.robertx22.age_of_exile.database.data.stats.types.core_stats.base;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCoreStat extends Stat implements ICoreStat {

    public BaseCoreStat(List<OptScaleExactStat> stats) {
        this.scaling = StatScaling.LINEAR;
        this.min_val = 0;
        this.stats = stats;
        this.statGroup = StatGroup.CORE;
    }

    List<OptScaleExactStat> stats;

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public final List<OptScaleExactStat> statsThatBenefit() {
        return stats;
    }

    @Override
    public final String locDescForLangFile() {
        // because i tend to change things and then the damn tooltip becomes outdated.
        String str = "Determines your total: ";
        for (OptScaleExactStat x : this.statsThatBenefit()) {
            str += x.getStat()
                .translate() + ", ";
        }
        str = str.substring(0, str.length() - 2);

        return str;
    }

    public float getPercent(StatData data) {

        return ((data.getAverageValue() - 10) / 10) * 100;
    }

    public List<Text> getCoreStatTooltip(EntityCap.UnitData unitdata, StatData data) {

        TooltipInfo info = new TooltipInfo(unitdata, null);

        int perc = (int) getPercent(data);

        List<Text> list = new ArrayList<>();
        list.add(
            new LiteralText("Stats that benefit: ").formatted(Formatting.GREEN));
        getMods(perc, unitdata.getLevel()).forEach(x -> list.addAll(x.GetTooltipString(info)));

        return list;

    }

    public List<ExactStatData> getMods(int perc, int lvl) {

        List<ExactStatData> list = new ArrayList<>();
        for (OptScaleExactStat x : this.statsThatBenefit()) {
            ExactStatData exactStatData = x.toExactStat(1);
            exactStatData.percentIncrease = perc;
            exactStatData.increaseByAddedPercent();
            list.add(exactStatData);
        }
        return list;

    }

    @Override
    public void addToOtherStats(EntityCap.UnitData unitdata, InCalcStatData data) {
        int perc = (int) getPercent(data.getCalculated());
        getMods(perc, unitdata.getLevel()).forEach(x -> x.applyStats(unitdata));
    }

}


