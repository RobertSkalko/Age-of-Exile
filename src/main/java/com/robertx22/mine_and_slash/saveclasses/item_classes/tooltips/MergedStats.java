package com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.text.MutableText;

public class MergedStats implements IGearPartTooltip {

    public List<TooltipStatInfo> list;

    public MergedStats(List<ExactStatData> list, TooltipInfo info) {

        List<TooltipStatInfo> infolist = new ArrayList<>();

        list.forEach(x -> infolist.add(new TooltipStatInfo(x, info)));

        this.list = TooltipStatInfo.mergeDuplicates(infolist);

        this.list.sort(Comparator.comparingInt(x -> -(int) (x.firstValue + x.secondValue)));
        this.list.sort(Comparator.comparing(x -> !x.type
            .equals(ModType.FLAT) && x.stat.IsPercent()));
    }

    @Override
    public List<MutableText> GetTooltipString(TooltipInfo info, GearItemData gear) {

        List<MutableText> tooltip = new ArrayList<>();

        list.forEach(x -> tooltip.addAll(x.GetTooltipString(info)));
        return tooltip;
    }

    @Override
    public Part getPart() {
        return Part.OTHER;
    }
}
