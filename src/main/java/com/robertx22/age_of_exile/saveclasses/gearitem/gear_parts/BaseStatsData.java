package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.tooltips.StatTooltipType;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Storable
public class BaseStatsData implements IRerollable, IStatsContainer, IGearPartTooltip {

    @Store
    public List<Integer> percents = new ArrayList<Integer>();

    @Store
    public String gear_type = "";

    @Override
    public void RerollFully(GearItemData gear) {

        percents = new ArrayList<>();
        this.gear_type = gear.gear_type;

        gear.GetBaseGearType()
            .baseStats()
            .forEach(x -> percents.add(getMinMax(gear).random()));

        gear.rare_prefix = RandomUtils.randomFromList(new ArrayList<>(RareItemAffixNames.prefixAny
            .keySet()));
        gear.rare_suffix = RandomUtils.randomFromList(new ArrayList<>(RareItemAffixNames.getSuffixMap(gear.GetBaseGearType())
            .keySet()));

    }

    @Override
    public void RerollNumbers(GearItemData gear) {
        RerollFully(gear);
    }

    static Formatting NUMBER_COLOR = Formatting.BLUE;
    static Formatting TEXT_COLOR = Formatting.GRAY;

    @Override
    public List<Text> GetTooltipString(TooltipInfo info, GearItemData gear) {

        List<ExactStatData> all = GetAllStats(gear);

        info.statTooltipType = StatTooltipType.BASE_LOCAL_STATS;

        List<Text> list = new ArrayList<>();
        list.add(new LiteralText(" "));

        List<Pair<Stat, List<Text>>> pairs = new ArrayList<>();

        List<StatModifier> stats = Database.GearTypes()
            .get(this.gear_type).base_stats;

        for (int i = 0; i < this.percents.size(); i++) {
            if (stats.size() > i && all.size() > i) {
                int perc = percents.get(i);
                pairs.add(new Pair(stats.get(i)
                    .GetStat(), info.statTooltipType.impl.getTooltipList(new TooltipStatWithContext(new TooltipStatInfo(all.get(i), perc, info), stats.get(i), gear.level))));
            }
        }

        pairs.sort(Comparator.comparingInt(x -> x.getLeft().baseStatTooltipOrder));

        pairs.forEach(x -> {
            list.addAll(x.getRight());
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
            int i = 0;
            for (StatModifier mod : Database.GearTypes()
                .get(gear_type)
                .baseStats()) {
                local.add(mod.ToExactStat(percents.get(i), gear.level));
                i++;
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
