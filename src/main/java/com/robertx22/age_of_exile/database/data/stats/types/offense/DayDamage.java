package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class DayDamage extends Stat {

    public static String GUID = "day_dmg";

    private DayDamage() {
        this.scaling = StatScaling.NONE;
        this.statEffect = new Effect();
    }

    public static DayDamage getInstance() {
        return DayDamage.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases dmg at day.";
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
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Damage at Day";
    }

    private static class Effect extends BaseDamageIncreaseEffect {
        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.source.world.isDay();
        }
    }

    private static class SingletonHolder {
        private static final DayDamage INSTANCE = new DayDamage();
    }
}

