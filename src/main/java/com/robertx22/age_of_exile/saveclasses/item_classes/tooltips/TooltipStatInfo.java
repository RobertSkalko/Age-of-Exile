package com.robertx22.age_of_exile.saveclasses.item_classes.tooltips;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TooltipStatInfo implements ITooltipList {

    public Stat stat;

    public float firstValue;
    public float secondValue;

    public ModType type;

    public TooltipInfo tooltipInfo;

    public TooltipStatInfo(ExactStatData data, TooltipInfo info) {
        this.stat = data.getStat();
        this.firstValue = data.getFirstValue();
        this.secondValue = data.getSecondValue();
        this.type = data.getType();
        this.tooltipInfo = info;
    }

    public TooltipStatInfo(OptScaleExactStat data, TooltipInfo info) {
        this.stat = data.getStat();
        this.firstValue = data.first;
        this.secondValue = data.second;
        this.type = data.getModType();
        this.tooltipInfo = info;
    }

    public boolean shouldShowDescriptions() {
        return tooltipInfo.shouldShowDescriptions();
    }

    public boolean useInDepthStats() {
        return tooltipInfo.useInDepthStats();
    }

    public void combine(TooltipStatInfo another) {
        this.firstValue += another.firstValue;
        this.secondValue += another.secondValue;

    }

    public boolean canBeCombined(TooltipStatInfo another) {
        return stat.GUID()
            .equals(another.stat.GUID()) && type.equals(another.type);
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        return stat.getTooltipList(this);
    }

    public static List<TooltipStatInfo> mergeDuplicates(List<TooltipStatInfo> duplicates) {

        List<TooltipStatInfo> list = new ArrayList<>();

        for (TooltipStatInfo duplicate : duplicates) {

            Optional<TooltipStatInfo> found = list.stream()
                .filter(x -> x.canBeCombined(duplicate))
                .findFirst();

            if (found.isPresent()) {
                found.get()
                    .combine(duplicate);

            } else {
                list.add(duplicate);
            }
        }

        return list;

    }

}
