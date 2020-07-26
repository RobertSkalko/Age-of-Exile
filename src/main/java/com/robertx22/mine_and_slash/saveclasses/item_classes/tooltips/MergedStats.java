package com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips;

import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergedStats implements IGearPartTooltip {

    public List<TooltipStatInfo> list;

    public MergedStats(List<ExactStatData> list, TooltipInfo info) {

        List<TooltipStatInfo> infolist = new ArrayList<>();

        list.forEach(x -> infolist.add(new TooltipStatInfo(x, info)));

        this.list = TooltipStatInfo.mergeDuplicates(infolist);

        // local stats first, then others, and lastly ele resists
        this.list.sort(Comparator.comparing(x -> {

            if (x.stat.isLocal()) {
                return 0;
            }
            if (x.stat instanceof ElementalResist) {
                return 2;
            }

            return 1;
        }));

    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info, GearItemData gear) {

        List<Text> tooltip = new ArrayList<>();

        list.forEach(x -> tooltip.addAll(x.GetTooltipString(info)));
        return tooltip;
    }

    @Override
    public Part getPart() {
        return Part.OTHER;
    }
}
