package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.tooltips.StatTooltipType;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.library_of_exile.utils.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Storable
public class BaseStatsData implements IRerollable, IStatsContainer, IGearPartTooltip {

    @Store
    public List<Integer> perc = new ArrayList<Integer>();

    @Override
    public void RerollFully(GearItemData gear) {

        perc = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            perc.add(getMinMax(gear).random());

            gear.rp = RandomUtils.randomFromList(new ArrayList<>(RareItemAffixNames.prefixAny
                .keySet()));
            gear.rs = RandomUtils.randomFromList(new ArrayList<>(RareItemAffixNames.getSuffixMap(gear.GetBaseGearType())
                .keySet()));
        }

    }

    @Override
    public void RerollNumbers(GearItemData gear) {
        RerollFully(gear);
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info, GearItemData gear) {

        List<ExactStatData> all = GetAllStats(gear);

        info.statTooltipType = StatTooltipType.BASE_LOCAL_STATS;

        List<ITextComponent> list = new ArrayList<>();
        list.add(new StringTextComponent(" "));

        List<Tuple<Stat, List<ITextComponent>>> pairs = new ArrayList<>();

        List<StatModifier> stats = gear.GetBaseGearType().base_stats;

        if (gear.uniqueBaseStatsReplaceBaseStats()) {
            stats = gear.uniqueStats.getUnique(gear)
                .base_stats;
        }

        if (!info.useInDepthStats()) {

        }
        ExactStatData.combine(all);

        for (int i = 0; i < this.perc.size(); i++) {
            if (all.size() > i) {
                int perc = this.perc.get(i);
                if (all.size() > i) {

                    TooltipStatInfo ctx = new TooltipStatInfo(all.get(i), perc, info);

                    pairs.add(new Tuple(all.get(i)
                        .getStat()
                        , info.statTooltipType.impl.getTooltipList(gear.getRarity()
                        .textFormatting(), new TooltipStatWithContext(ctx, stats.size() > i ? stats.get(i) : null, (int) gear.getEffectiveLevel()))));
                }
            }
        }

        pairs.sort(Comparator.comparingInt(x -> x.getA().order));

        pairs.forEach(x -> {
            list.addAll(x.getB());
        });

        info.statTooltipType = StatTooltipType.NORMAL;

        return list;

    }

    @Override
    public boolean isBaseStats() {
        return true;
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {

        List<ExactStatData> local = new ArrayList<>();

        try {

            if (!gear.uniqueBaseStatsReplaceBaseStats()) {
                int i = 0;
                for (StatModifier mod : gear.GetBaseGearType()
                    .baseStats()) {
                    local.add(mod.ToExactStat(perc.get(i), gear.getEffectiveLevel()));
                    i++;
                }
            } else {
                int n = 0;
                for (StatModifier mod : gear.uniqueStats.getUnique(gear)
                    .base_stats) {
                    local.add(mod.ToExactStat(perc.get(n), gear.getEffectiveLevel()));
                    n++;
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
}
