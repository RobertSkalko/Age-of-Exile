package com.robertx22.age_of_exile.saveclasses.gearitem.rework;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergedStats implements IGearPartTooltip {

    public List<ExactStatData> mergedList = new ArrayList<>();

    public MergedStats(List<StatModifierInfo> stats) {

        stats.removeIf(x -> x.exactStat.getStat().is_long);

        for (StatModifierInfo stat : stats) {

            mergedList.add(stat.exactStat);
        }

        ExactStatData.combine(mergedList);

        this.mergedList.sort(Comparator.comparingInt(x -> {
            if (x.getType()
                .isFlat()) {
                return 0;
            } else {
                return 1;
            }
        }));

    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<ITextComponent> tooltip = new ArrayList<>();
        mergedList.forEach(x -> tooltip.addAll(x.getStat()
            .getTooltipList(new ExactStatInfo(x))));
        return tooltip;
    }

    @Override
    public Part getPart() {
        return Part.OTHER;
    }
}
