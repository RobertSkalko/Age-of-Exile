package com.robertx22.age_of_exile.database.data.stats.types.core_stats.base;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.StatModifier;
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

    public BaseCoreStat() {
        this.scaling = StatScaling.LINEAR;
        this.min_val = 0;
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

    public float getPercent(InCalcStatData data) {
        return data.getFlatAverage() * 100;
    }

    public List<Text> getCoreStatTooltip(EntityCap.UnitData unitdata, StatData data) {

        TooltipInfo info = new TooltipInfo(unitdata, null);

        List<Text> list = new ArrayList<>();
        list.add(
            new LiteralText("Stats that benefit: ").formatted(Formatting.GREEN));
        this.statsThatBenefit()
            .forEach(x -> list.addAll(x.getEstimationTooltip(unitdata.getLevel()))); // todo probably wrong

        return list;

    }

    public List<ExactStatData> getMods(InCalcStatData data, int lvl) {
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
    public void addToOtherStats(EntityCap.UnitData unitdata, InCalcStatData data) {
        getMods(data, unitdata.getLevel()).forEach(x -> x.applyStats(unitdata));
    }

}


