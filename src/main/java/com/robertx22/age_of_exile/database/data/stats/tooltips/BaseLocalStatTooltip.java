package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class BaseLocalStatTooltip implements IStatTooltipType {

    @Override
    public List<Text> getTooltipList(TooltipStatWithContext ctx) {
        TooltipStatInfo info = ctx.statinfo;

        List<Text> list = new ArrayList<Text>();

        list.add(new LiteralText(Formatting.GRAY + info.stat.getStatNameRegex()
            .translate(info.type, info.firstValue, info.secondValue, info.stat)));

        /*
        if (info.useInDepthStats()) {
            if (info.statRange != null) {
                text.appendSibling(getNumberRanges(info.statRange));
            }
        }
                 */
        if (info.shouldShowDescriptions()) {
            list.addAll(info.stat.getCutDescTooltip());
        }

        return list;

    }
}
