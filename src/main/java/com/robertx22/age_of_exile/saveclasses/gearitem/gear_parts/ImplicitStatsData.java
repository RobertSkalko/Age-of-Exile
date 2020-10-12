package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IRerollable;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Storable
public class ImplicitStatsData implements IGearPartTooltip, IRerollable, IStatsContainer {

    @Store
    public Integer percent = 0;

    @Override
    public void RerollFully(GearItemData gear) {

        percent = getMinMax(gear).random();

    }

    @Override
    public void RerollNumbers(GearItemData gear) {
        RerollFully(gear);
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info, GearItemData gear) {

        List<Text> list = new ArrayList<>();

        List<ExactStatData> stats = GetAllStats(gear);

        if (!stats.isEmpty()) {
            list.add(new SText(""));

            getAllStatsWithCtx(gear, info)
                .forEach(x -> list.addAll(x.GetTooltipString(info)));
        }
        return list;
    }

    @Override
    public Part getPart() {
        return Part.IMPLICIT_STATS;
    }

    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, TooltipInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();
        gear.GetBaseGearType()
            .implicitStats()
            .forEach(x -> {
                ExactStatData exact = x.ToExactStat(percent, gear.level);
                list.add(new TooltipStatWithContext(new TooltipStatInfo(exact, info), x, gear.level));
            });
        return list;
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {
        return gear.GetBaseGearType()
            .implicitStats()
            .stream()
            .map(x -> x.ToExactStat(percent, gear.level))
            .collect(Collectors.toList());
    }
}