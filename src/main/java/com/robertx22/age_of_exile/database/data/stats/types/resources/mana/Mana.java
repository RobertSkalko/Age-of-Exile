package com.robertx22.age_of_exile.database.data.stats.types.resources.mana;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.Formatting;

public class Mana extends Stat {
    public static String GUID = "mana";

    public static Mana getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Mana is used to cast spells";
    }

    private Mana() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.format = Formatting.BLUE.getName();
        this.icon = "\u262F";
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
        return "Mana";
    }

    private static class SingletonHolder {
        private static final Mana INSTANCE = new Mana();
    }
}
