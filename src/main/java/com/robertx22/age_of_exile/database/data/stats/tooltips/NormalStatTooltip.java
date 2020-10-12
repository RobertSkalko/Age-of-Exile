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
            .translate(info.type, info.firstValue, info.secondValue, info.stat));

        if (ctx.showStatRanges()) {
            txt.append(" ")
                .append(ctx.mod.getRangeToShow(ctx.level));
        }

        list.add(txt);

        if (info.shouldShowDescriptions()) {
            list.addAll(info.stat.getCutDescTooltip());
        }

        return list;

    }

}
