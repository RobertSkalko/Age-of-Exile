package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class BaseLocalStatTooltip implements IStatTooltipType {

    @Override
    public List<ITextComponent> getTooltipList(TextFormatting format, TooltipStatWithContext ctx) {
        TooltipStatInfo info = ctx.statinfo;

        List<ITextComponent> list = new ArrayList<ITextComponent>();
        if (true) {
            String icon = "\u25CF";
            list.add(new StringTextComponent(icon + " ")
                .append(info.stat.locName())
                .append(": ")
                .withStyle(format != null ? format : TextFormatting.WHITE)
                .append(new StringTextComponent((int) info.firstValue + "")
                    .withStyle(TextFormatting.GRAY)));

            return list;

        }
        String icon = TextFormatting.RED + info.stat.icon + " ";

        if (ctx.statinfo.stat.is_long) {
            icon = "";
        }

        IFormattableTextComponent txt = new StringTextComponent(StatNameRegex.BASIC_LOCAL
            .translate(format, ctx, info.type, info.firstValue, info.stat));

        if (ctx.statinfo.stat.is_long) {
            return longStat(ctx, txt);
        }

        if (ctx.showStatRanges()) {
            txt.append(" ")
                .append(NormalStatTooltip.getPercentageView(ctx.statinfo.percent));
        }

        list.add(txt);

        if (info.shouldShowDescriptions()) {
            list.addAll(info.stat.getCutDescTooltip());
        }

        return list;

    }
}
