package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.crit.CriticalHitEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class NonCritDamage extends Stat {

    private NonCritDamage() {
        this.scaling = StatScaling.SLOW;
        this.group = StatGroup.MAIN;

        this.statEffect = new Effect();
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
    public String locNameForLangFile() {
        return "Non Critical Damage";
    }

    private static class Effect extends BaseDamageIncreaseEffect {

        @Override
        public int GetPriority() {
            return Priority.afterThis(CriticalHitEffect.getInstance()
                .GetPriority());
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.isCriticalHit();
        }

    }

    private static class SingletonHolder {
        private static final NonCritDamage INSTANCE = new NonCritDamage();
    }
}
