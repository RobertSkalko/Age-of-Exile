package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.tooltips.StatTooltipType;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatsWithContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

@Storable
public class BaseStatsData implements IRerollable, IStatsContainer, IGearPartTooltip, StatContainer {

    @Store
    public Integer perc = 50;

    @Override
    public void RerollFully(GearItemData gear) {
        perc = getMinMax(gear).random();

        gear.rp = RandomUtils.randomFromList(new ArrayList<>(RareItemAffixNames.prefixAny
            .keySet()));
        gear.rs = RandomUtils.randomFromList(new ArrayList<>(RareItemAffixNames.getSuffixMap(gear.GetBaseGearType())
            .keySet()));

    }

    @Override
    public void RerollNumbers(GearItemData gear) {
        RerollFully(gear);
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<ExactStatData> all = GetAllStats(gear);

        info.statTooltipType = StatTooltipType.NORMAL;

        List<ITextComponent> list = new ArrayList<>();
        list.add(new StringTextComponent(" "));

        List<Tuple<Stat, List<ITextComponent>>> pairs = new ArrayList<>();

        ExactStatData.combine(all);

        for (ExactStatData stat : all) {
            TooltipStatInfo ctx = new TooltipStatInfo(stat, perc, info);
            list.addAll(ctx.GetTooltipString(info));
        }

        info.statTooltipType = StatTooltipType.NORMAL;

        return list;

    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {

        List<ExactStatData> local = new ArrayList<>();

        try {

            if (!gear.uniqueBaseStatsReplaceBaseStats()) {
                for (StatModifier mod : gear.GetBaseGearType()
                    .baseStats()) {
                    local.add(mod.ToExactStat(perc, gear.getILVL()));

                }
            } else {
                for (StatModifier mod : gear.uniqueStats.getUnique(gear)
                    .base_stats) {
                    local.add(mod.ToExactStat(perc, gear.getILVL()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return local;
    }

    @Override
    public Part getPart() {
        return Part.BASE_STATS;
    }

    @Override
    public StatsWithContext getStatsWithContext() {
// todo

        return null;
    }
}
