package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import net.minecraft.text.Text;

import java.util.List;

public interface IStatTooltipType {
    List<Text> getTooltipList(TooltipStatInfo info);

}
