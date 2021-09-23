package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.text.TextFormatting;

public class SpellPower extends Stat {

    public static SpellPower getInstance() {
        return SpellPower.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Used for certain spells";
    }

    public static String GUID = "spell_power";

    private SpellPower() {

        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.icon = "\u2748";
        this.format = TextFormatting.BLUE.getName();

    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Spell Power";
    }

    private static class SingletonHolder {
        private static final SpellPower INSTANCE = new SpellPower();
    }
}
