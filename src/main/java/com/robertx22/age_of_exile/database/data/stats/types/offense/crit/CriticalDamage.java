package com.robertx22.age_of_exile.database.data.stats.types.offense.crit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.crit.CriticalDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class CriticalDamage extends Stat implements IStatEffects {

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
        return "Critical Damage";
    }

    private static class SingletonHolder {
        private static final CriticalDamage INSTANCE = new CriticalDamage();
    }
}
