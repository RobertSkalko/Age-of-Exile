package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public interface IStatTooltipType {
    List<Text> getTooltipList(TooltipStatWithContext info);

    default List<Text> longStat(TooltipStatWithContext ctx, MutableText txt) {
        List<Text> list = new ArrayList<>();

        int i = 0;
        for (MutableText x : TooltipUtils.cutIfTooLong(txt, Formatting.GRAY)) {
            if (i == 0) {
                x = new LiteralText(Formatting.LIGHT_PURPLE + "\u25C6" + " " + Formatting.GRAY).append(x);
            } else {
                x = new LiteralText(" ").append(x);
            }
            list.add(x);
            i++;
        }
        return list;

    }

}
