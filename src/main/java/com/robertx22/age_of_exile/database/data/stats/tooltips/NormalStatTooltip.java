package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class NormalStatTooltip implements IStatTooltipType {

    @Override
    public List<Text> getTooltipList(TooltipStatWithContext ctx) {

        TooltipStatInfo info = ctx.statinfo;

        List<Text> list = new ArrayList<>();

        MutableText txt = new LiteralText(Formatting.BLUE + info.stat.getStatNameRegex()
            .translate(ctx, info.type, info.firstValue, info.secondValue, info.stat));

        if (ctx.statinfo.stat.isLongStat) {
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

    public static MutableText getPercentageView(int perc) {
        Formatting format = Formatting.RED;
        if (perc > 25) {
            format = Formatting.YELLOW;
        }
        if (perc > 50) {
            format = Formatting.GREEN;
        }
        if (perc > 75) {
            format = Formatting.AQUA;
        }
        if (perc > 90) {
            format = Formatting.LIGHT_PURPLE;
        }

        return new LiteralText(format + " [" + perc + "%]").formatted(format);

    }

}
