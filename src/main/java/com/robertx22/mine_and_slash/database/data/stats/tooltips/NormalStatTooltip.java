package com.robertx22.mine_and_slash.database.data.stats.tooltips;

import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatInfo;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class NormalStatTooltip implements IStatTooltipType {

    @Override
    public List<Text> getTooltipList(TooltipStatInfo info) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText(Formatting.BLUE + info.stat.getStatNameRegex()
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
