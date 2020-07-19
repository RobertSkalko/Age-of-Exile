package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.text.MutableText;

public interface IAutoLocMultiLore extends IBaseAutoLoc {

    public default String getMultiGroupName() {
        return locLoresGroup().name().toUpperCase().replaceAll("_", " ") + " - LORE LINES";
    }

    public AutoLocGroup locLoresGroup();

    List<String> loreLines();

    default String locMultiLoreLangFileGUID() {
        return this.formattedGUID();
    }

    public default String formattedLocLoresLangFileGUID() {
        return getPrefix() + getFormatedForLangFile(locMultiLoreLangFileGUID());
    }

    default List<MutableText> getComponents() {
        List<MutableText> list = new ArrayList<>();
        for (String prefix : getPrefixListForLangFile()) {
            list.add(CLOC.blank(prefix + this.formattedLocLoresLangFileGUID()));
        }
        return list;
    }

    default List<String> getPrefixListForLangFile() {

        List<String> list = new ArrayList<>();

        int i = 0;
        for (String line : loreLines()) {
            list.add("lore_" + i++ + "_");
        }

        return list;

    }
}
