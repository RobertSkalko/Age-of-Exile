package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class NormalStatTooltip implements IStatTooltipType {

    @Override
    public List<ITextComponent> getTooltipList(TextFormatting format, TooltipStatWithContext ctx) {

        TooltipStatInfo info = ctx.statinfo;

        List<ITextComponent> list = new ArrayList<>();

        IFormattableTextComponent txt = new StringTextComponent(info.stat.getStatNameRegex()
            .translate(format, ctx, info.type, info.firstValue, info.stat));

        if (ctx.statinfo.stat.is_long) {
            return longStat(ctx, txt);
        }

        if (ctx.showStatRanges()) {
            txt.append(" ")
                .append(getPercentageView(ctx.statinfo.percent));
        }

        list.add(txt);

        if (info.shouldShowDescriptions()) {
            list.addAll(info.stat.getCutDescTooltip());
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
