package com.robertx22.age_of_exile.database.data.stats.types.loot;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.text.TextFormatting;

public class MagicFind extends Stat {

    private MagicFind() {
        this.icon = "\u2663";
        this.format = TextFormatting.AQUA.getName();
    }

    public static MagicFind getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Adds chance for higher rarity of items found in chests.";
    }

    @Override
    public String GUID() {
        return "magic_find";
    }

    @Override
    public String locNameForLangFile() {
        return "Magic Find";
    }

    private static class SingletonHolder {
        private static final MagicFind INSTANCE = new MagicFind();
    }
}
