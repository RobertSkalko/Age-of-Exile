package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.util.text.IFormattableTextComponent;

public interface IAutoLocDesc extends IBaseAutoLoc {

    public default String getDescGroupName() {
        return locDescGroup().name()
            .toUpperCase()
            .replaceAll("_", " ") + " - DESCRIPTIONS";
    }

    public AutoLocGroup locDescGroup();

    String locDescLangFileGUID();

    String locDescForLangFile();

    default boolean shouldRegisterLangDesc() {
        return true;
    }

    public default IFormattableTextComponent locDesc() {
        return CLOC.blank(formattedLocDescLangFileGUID());
    }

    public default String formattedLocDescLangFileGUID() {
        return getPrefix() + getFormatedForLangFile(locDescLangFileGUID());
    }

}

