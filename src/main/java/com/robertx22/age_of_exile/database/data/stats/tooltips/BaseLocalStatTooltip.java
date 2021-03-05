package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class BaseLocalStatTooltip implements IStatTooltipType {

    @Override
    public List<Text> getTooltipList(TooltipStatWithContext ctx) {
        TooltipStatInfo info = ctx.statinfo;

        List<Text> list = new ArrayList<Text>();

        MutableText txt = new LiteralText(info.stat.textFormat + info.stat.textIcon + " " + StatNameRegex.BASIC_LOCAL
            .translate(info.type, info.firstValue, info.secondValue, info.stat));

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
