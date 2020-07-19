package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import net.minecraft.text.MutableText;

public interface IAutoLocName extends IBaseAutoLoc {

    public default String getGroupName() {
        return locNameGroup().name()
            .toUpperCase()
            .replaceAll("_", " ") + " - NAMES";
    }

    AutoLocGroup locNameGroup();

    String locNameLangFileGUID();

    default boolean shouldRegisterLangName() {
        return true;
    }

    public default String formattedLocNameLangFileGUID() {
        return getPrefix() + getFormatedForLangFile(locNameLangFileGUID());
    }

    public default String translate() {
        return CLOC.translate(this.locName());
    }

    public String locNameForLangFile();

    public default MutableText locName() {
        return CLOC.blank(getFormatedForLangFile(locNameLangFileGUID()));
    }

}
