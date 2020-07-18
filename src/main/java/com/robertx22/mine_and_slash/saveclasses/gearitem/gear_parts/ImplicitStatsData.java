package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IRerollable;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.text.Text;

@Storable
public class ImplicitStatsData implements IGearPartTooltip, IRerollable, IStatsContainer {

    @Store
    public Integer percent = 0;

    @Store
    public String gear_type = "";

    @Override
    public void RerollFully(GearItemData gear) {

        percent = getMinMax(gear).random();
        this.gear_type = gear.gear_type;

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

            GetAllStats(gear)
                .forEach(x -> list.addAll(x.GetTooltipString(info)));
        }
        return list;
    }

    @Override
    public Part getPart() {
        return Part.IMPLICIT_STATS;
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