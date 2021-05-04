package com.robertx22.age_of_exile.saveclasses.item_classes.tooltips;

import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.misc.MoreSocketsStat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergedStats implements IGearPartTooltip {

    public List<TooltipStatInfo> list;

    public MergedStats(List<ExactStatData> list, TooltipInfo info, GearItemData gear) {

        list.removeIf(x -> x.getStat() instanceof MoreSocketsStat || x.getStat().is_long);

        list.removeIf(x -> x.getStat().isLocalTo.apply(gear.GetBaseGearType()) && x.getType()
            .isFlat());

        List<TooltipStatInfo> infolist = new ArrayList<>();

        list.forEach(x -> infolist.add(new TooltipStatInfo(x, -99, info)));

        this.list = TooltipStatInfo.mergeDuplicates(infolist);

        // local stats first, then others, and lastly ele resists
        this.list.sort(Comparator.comparing(x -> {

            if (x.stat.isLocalTo.apply(gear.GetBaseGearType())) {
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

        // info.statTooltipType = StatTooltipType.BASE_LOCAL_STATS; // todo test

        list.forEach(x -> tooltip.addAll(x.GetTooltipString(info)));
        return tooltip;
    }

    @Override
    public Part getPart() {
        return Part.OTHER;
    }
}
