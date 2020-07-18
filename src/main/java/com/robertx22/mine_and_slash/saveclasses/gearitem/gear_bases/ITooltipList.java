package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import java.util.List;
import net.minecraft.text.Text;

public interface ITooltipList {

    public default List<Text> GetTooltipStringWithNoExtraSpellInfo(TooltipInfo info) {
        info.showAbilityExtraInfo = false;
        List<Text> list = GetTooltipString(info);
        info.showAbilityExtraInfo = true;
        return list;
    }

    public abstract List<Text> GetTooltipString(TooltipInfo info);
}


