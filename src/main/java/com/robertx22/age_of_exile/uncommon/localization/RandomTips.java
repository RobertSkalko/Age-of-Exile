package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

public enum RandomTips implements IAutoLocName {
    Wiki("There is an in-game wiki! It shows useful things like what unique gears exist, how to get them, what level mobs are in which dimensions and so on! Go check it out in the Main Hub! (Default key: H)"),
    Proffwiki("Most professions have wiki sections, like what ores reward mining experience! Check it in the profession screen!"),
    Proffslvling("There are many professions and leveling them provide benefits, including bonus experience when killing mobs!"),
    Favor("You gain favor when opening chests found in structures for the first time. Favor is expended when you gain loot. At zero favor, you can't obtain great loot and your experience gain is lowered!"),
    Foodtoregen("You won't regenerate health or mana if your hunger bar is low.");

    private String localization = "";

    RandomTips(String str) {
        this.localization = str;

    }

    @Override
    public IAutoLocName.AutoLocGroup locNameGroup() {
        return AutoLocGroup.Chat_Messages;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".tips." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return localization;
    }

    @Override
    public String GUID() {
        return this.name()
            .toLowerCase(Locale.ROOT);
    }
}

