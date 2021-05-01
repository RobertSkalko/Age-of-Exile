package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class DotDamage extends Stat {

    public static String GUID = "dot_dmg";

    private DotDamage() {
        this.scaling = StatScaling.NONE;
        this.statEffect = new Effect();
    }

    public static DotDamage getInstance() {
        return DotDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases damage over time.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.All;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Damage over Time";
    }

    private static class Effect extends BaseDamageIncreaseEffect {
        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.getAttackType()
                .isDot();
        }
    }

    private static class SingletonHolder {
        private static final DotDamage INSTANCE = new DotDamage();
    }
}