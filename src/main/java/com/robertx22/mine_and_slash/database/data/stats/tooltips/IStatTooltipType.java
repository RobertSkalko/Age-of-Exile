package com.robertx22.mine_and_slash.database.data.stats.tooltips;

import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatInfo;
import net.minecraft.text.Text;

import java.util.List;

public interface IStatTooltipType {
    List<Text> getTooltipList(TooltipStatInfo info);

}
