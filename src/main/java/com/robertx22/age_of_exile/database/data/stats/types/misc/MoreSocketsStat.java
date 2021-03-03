package com.robertx22.age_of_exile.database.data.stats.types.misc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class MoreSocketsStat extends Stat {
    public static String GUID = "socket_slots";

    public static MoreSocketsStat getInstance() {
        return MoreSocketsStat.SingletonHolder.INSTANCE;
    }

    private MoreSocketsStat() {
        this.min_val = -100;
        this.scaling = StatScaling.NONE;
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
        return "Socket Slots";
    }

    @Override
    public String locDescForLangFile() {
        return "Adds socket slots to item.";
    }

    private static class SingletonHolder {
        private static final MoreSocketsStat INSTANCE = new MoreSocketsStat();
    }
}

