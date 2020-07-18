package com.robertx22.mine_and_slash.database.data.stats.types;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class UnknownStat extends Stat {
    public static String GUID = "renamed_or_deleted_stat";

    public UnknownStat() {

    }

    @Override
    public boolean IsShownOnStatGui() {
        return false;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Unknown";
    }
}
