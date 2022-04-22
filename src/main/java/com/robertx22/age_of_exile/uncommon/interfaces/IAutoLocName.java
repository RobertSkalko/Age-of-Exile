package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.library_of_exile.text_wrapper.ModTranslatedText;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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

    public default IFormattableTextComponent locName(Object[] args) {
        return new TranslationTextComponent(locNameLangFileGUID(), args);
    }

    public default IFormattableTextComponent locName() {
        return CLOC.blank(getFormatedForLangFile(locNameLangFileGUID()));
    }

    public default ModTranslatedText ModlocName() {
        return new ModTranslatedText(getFormatedForLangFile(locNameLangFileGUID()));
    }

}
