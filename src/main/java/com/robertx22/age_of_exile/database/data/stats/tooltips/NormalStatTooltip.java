package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatModifierInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatTooltipInfo;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class NormalStatTooltip implements IStatTooltipType {

    @Override
    public List<ITextComponent> getTooltipList(TextFormatting format, StatTooltipInfo info) {

        TooltipInfo tinfo = new TooltipInfo();
        List<ITextComponent> list = new ArrayList<>();

        IFormattableTextComponent txt = new StringTextComponent(info.getStat()
            .getStatNameRegex()
            .translate(format, info, info.getType(), info.getValue(), info.getStat()));

        if (info.getStat().is_long) {
            return longStat(info, txt);
        }

        if (tinfo.hasShiftDown) {
            if (info instanceof StatModifierInfo) {
                StatModifierInfo modinfo = (StatModifierInfo) info;
                txt.append(" ")
                    .append(getPercentageView(modinfo.percent.get()));
            }
        }

        list.add(txt);

        if (tinfo.hasAltDown) {
            list.addAll(info.getStat()
                .getCutDescTooltip());
        }

        return list;

    }

    public static IFormattableTextComponent getPercentageView(int perc) {
        TextFormatting format = TextFormatting.RED;
        if (perc > 25) {
            format = TextFormatting.YELLOW;
        }
        if (perc > 50) {
            format = TextFormatting.GREEN;
        }
        if (perc > 75) {
            format = TextFormatting.AQUA;
        }
        if (perc > 90) {
            format = TextFormatting.LIGHT_PURPLE;
        }

        return new StringTextComponent(format + " [" + perc + "%]").withStyle(format);

    }

}
