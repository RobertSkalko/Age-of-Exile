package com.robertx22.mine_and_slash.database.data.stats.types.loot;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class MagicFind extends Stat {

    private MagicFind() {
    }

    public static MagicFind getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsShownOnStatGui() {
        return true;
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
        return "Adds chance for higher rarity of items";
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
