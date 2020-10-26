package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.TotalDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class TotalDamage extends Stat implements IStatEffects {

    private TotalDamage() {
        this.scaling = StatScaling.SLOW;
        this.statGroup = StatGroup.MAIN;
    }

    public static String GUID = "total_damage";

    public static TotalDamage getInstance() {
        return TotalDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases all your damage. Attacks, spells etc.";
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
        return TotalDamageEffect.getInstance();
    }

    @Override
    public String locNameForLangFile() {
        return "Total Damage";
    }

    private static class SingletonHolder {
        private static final TotalDamage INSTANCE = new TotalDamage();
    }
}