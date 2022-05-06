package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.CoreStatData;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ICoreStat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.wrapped_primitives.RpgLevel;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoreStat extends BaseDatapackStat implements ICoreStat {

    public static String SER_ID = "core_stat";

    public CoreStatData data = new CoreStatData();

    transient String locname;

    public CoreStat(String id, String locname, CoreStatData data) {
        super(SER_ID);

        this.id = id;

        this.data = data;

        this.is_perc = true;
        this.min = 0;
        this.group = StatGroup.CORE;
        this.scaling = StatScaling.NORMAL;
        this.is_long = false;

        this.locname = locname;
    }

    @Override
    public final String locDescForLangFile() {
        // because i tend to change things and then the damn tooltip becomes outdated.
        String str = "Determines your total: ";
        for (OptScaleExactStat x : this.statsThatBenefit()) {
            str += x.getStat()
                .translate() + ", ";
        }
        str = str.substring(0, str.length() - 2);

        return str;
    }

    public float getValue(StatData data) {
        return data.getValue();
    }

    public List<ITextComponent> getCoreStatTooltip(EntityData unitdata, StatData data) {

        TooltipInfo info = new TooltipInfo(unitdata, null);

        int val = (int) getValue(data);

        List<ITextComponent> list = new ArrayList<>();

        list.add(
            new StringTextComponent("For each point: ").withStyle(TextFormatting.GREEN));
        getMods(1).forEach(x -> list.addAll(x.GetTooltipString(new RpgLevel(1))));

        list.add(new SText(""));

        list.add(
            new StringTextComponent("Total: ").withStyle(TextFormatting.GREEN));
        getMods(val).forEach(x -> list.addAll(x.GetTooltipString(new RpgLevel(1))));

        return list;

    }

    public List<ExactStatData> getMods(int amount) {

        return data.stats.stream()
            .map(x -> {
                ExactStatData exact = ExactStatData.of(amount * x.v1, x.getStat()
                    .getStat(), x.getStat()
                    .getModType(), 1);
                return exact;
            })
            .collect(Collectors.toList());

    }

    @Override
    public final List<OptScaleExactStat> statsThatBenefit() {
        return data.stats.stream()
            .map(e -> e.getStat())
            .collect(Collectors.toList());
    }

    @Override
    public void addToOtherStats(EntityData unitdata, InCalcStatData data) {

        for (ExactStatData x : getMods((int) data.getValue())) {
            x.applyStats(unitdata);
        }
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

}