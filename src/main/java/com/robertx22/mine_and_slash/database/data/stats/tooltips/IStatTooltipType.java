package com.robertx22.mine_and_slash.database.data.stats.tooltips;

import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatInfo;
import java.util.List;
import net.minecraft.text.Text;

public interface IStatTooltipType {
    List<Text> getTooltipList(TooltipStatInfo info);

}
