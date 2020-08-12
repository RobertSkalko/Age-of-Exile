package com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases;

import net.minecraft.text.Text;

import java.util.List;

public interface ITooltipList {

    public default List<Text> GetTooltipStringWithNoExtraSpellInfo(TooltipInfo info) {
        info.showAbilityExtraInfo = false;
        List<Text> list = GetTooltipString(info);
        info.showAbilityExtraInfo = true;
        return list;
    }

    public abstract List<Text> GetTooltipString(TooltipInfo info);
}


