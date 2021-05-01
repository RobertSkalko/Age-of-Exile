package com.robertx22.age_of_exile.database.data.stats.types.resources.blood;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.Formatting;

public class Blood extends Stat {
    public static String GUID = "blood";

    private Blood() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.icon = "\u2764";
        this.format = Formatting.DARK_RED;
    }

    public static Blood getInstance() {
        return Blood.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Blood is a unique resource that works like mana.";
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
        return "Blood";
    }

    private static class SingletonHolder {
        private static final Blood INSTANCE = new Blood();
    }
}
