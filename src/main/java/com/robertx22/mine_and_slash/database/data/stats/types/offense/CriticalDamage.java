package com.robertx22.mine_and_slash.database.data.stats.types.offense;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.offense.CriticalDamageEffect;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffects;

public class CriticalDamage extends Stat implements IStatEffects {

    public static String GUID = "critical_damage";

    public static CriticalDamage getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatGroup statGroup() {
        return StatGroup.Damage;
    }

    @Override
    public String locDescForLangFile() {
        return "If Critical, multiply by x";
    }

    @Override
    public String getIconPath() {
        return "crit_dmg";
    }

    @Override
    public IStatEffect getEffect() {
        return new CriticalDamageEffect();
    }

    private CriticalDamage() {
        this.BaseFlat = 20;
        this.minimumValue = 0;
        this.maximumValue = 400;
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
