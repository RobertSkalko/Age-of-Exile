package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatTooltipInfo;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public interface IStatTooltipType {

    List<ITextComponent> getTooltipList(TextFormatting format, StatTooltipInfo info);

    default List<ITextComponent> longStat(StatTooltipInfo ctx, IFormattableTextComponent txt) {
        List<ITextComponent> list = new ArrayList<>();

        int i = 0;
        for (IFormattableTextComponent x : TooltipUtils.cutIfTooLong(txt, TextFormatting.GRAY)) {
            if (i == 0) {
                x = new StringTextComponent(TextFormatting.LIGHT_PURPLE + "\u25C6" + " " + TextFormatting.GRAY).append(x);
            } else {
                x = new StringTextComponent(" ").append(x);
            }
            list.add(x);
            i++;
        }
        return list;

    }

}
