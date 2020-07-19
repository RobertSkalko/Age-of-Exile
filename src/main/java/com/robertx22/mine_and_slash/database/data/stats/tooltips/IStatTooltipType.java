package com.robertx22.mine_and_slash.database.data.stats.tooltips;

import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatInfo;
import java.util.List;

import net.minecraft.text.MutableText;

public interface IStatTooltipType {
    List<MutableText> getTooltipList(TooltipStatInfo info);

}
