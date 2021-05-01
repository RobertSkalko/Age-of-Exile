package com.robertx22.age_of_exile.database.data.stats.types.offense.crit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.crit.CriticalDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.util.Formatting;

public class CriticalDamage extends Stat {

    public static String GUID = "critical_damage";

    public static CriticalDamage getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "If Critical, multiply by x";
    }

    private CriticalDamage() {
        this.base = 50;
        this.min = 0;
        this.max = 500;
        this.group = StatGroup.MAIN;

        this.icon = "\u2694";
        this.format = Formatting.RED;

        this.statEffect = CriticalDamageEffect.getInstance();
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
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Crit Damage";
    }

    private static class SingletonHolder {
        private static final CriticalDamage INSTANCE = new CriticalDamage();
    }
}
