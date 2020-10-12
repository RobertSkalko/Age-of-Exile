package com.robertx22.age_of_exile.database.data.stats.tooltips;

import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import net.minecraft.text.Text;

import java.util.List;

public interface IStatTooltipType {
    List<Text> getTooltipList(TooltipStatWithContext info);

}
