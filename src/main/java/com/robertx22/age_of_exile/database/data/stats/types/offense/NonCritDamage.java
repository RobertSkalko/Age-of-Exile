package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.NonCritDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class NonCritDamage extends Stat implements IStatEffects {

    private NonCritDamage() {
        this.scaling = StatScaling.SLOW;
        this.statGroup = StatGroup.MAIN;
    }

    public static String GUID = "non_crit_damage";

    public static NonCritDamage getInstance() {
        return NonCritDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Modifies your non critical damage";
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
    public IStatEffect getEffect() {
        return NonCritDamageEffect.getInstance();
    }

    @Override
    public String locNameForLangFile() {
        return "Non Critical Damage";
    }

    private static class SingletonHolder {
        private static final NonCritDamage INSTANCE = new NonCritDamage();
    }
}
