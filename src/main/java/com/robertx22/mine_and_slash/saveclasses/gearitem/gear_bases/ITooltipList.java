package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import java.util.List;

import net.minecraft.text.MutableText;

public interface ITooltipList {

    public default List<MutableText> GetTooltipStringWithNoExtraSpellInfo(TooltipInfo info) {
        info.showAbilityExtraInfo = false;
        List<MutableText> list = GetTooltipString(info);
        info.showAbilityExtraInfo = true;
        return list;
    }

    public abstract List<MutableText> GetTooltipString(TooltipInfo info);
}


