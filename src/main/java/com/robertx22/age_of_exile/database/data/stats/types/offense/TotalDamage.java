package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class TotalDamage extends Stat {

    private TotalDamage() {
        this.scaling = StatScaling.SLOW;
        this.statGroup = StatGroup.MAIN;
        this.statEffect = new Effect();
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

    private static class Effect extends BaseDamageIncreaseEffect {

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return true;
        }

    }

    @Override
    public String locNameForLangFile() {
        return "Total Damage";
    }

    private static class SingletonHolder {
        private static final TotalDamage INSTANCE = new TotalDamage();
    }
}