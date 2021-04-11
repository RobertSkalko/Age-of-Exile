package com.robertx22.age_of_exile.database.data.stats.types.offense.crit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.crit.CriticalDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IExtraStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import net.minecraft.util.Formatting;

public class CriticalDamage extends Stat implements IExtraStatEffect {

    public static String GUID = "critical_damage";

    public static CriticalDamage getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "If Critical, multiply by x";
    }

    @Override
    public IStatEffect getEffect() {
        return CriticalDamageEffect.getInstance();
    }

    private CriticalDamage() {
        this.base_val = 50;
        this.min_val = 0;
        this.max_val = 500;
        this.statGroup = StatGroup.MAIN;

        this.textIcon = "\u2694";
        this.textFormat = Formatting.RED;
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
