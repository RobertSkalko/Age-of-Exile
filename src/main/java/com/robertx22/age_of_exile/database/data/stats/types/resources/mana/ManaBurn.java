package com.robertx22.age_of_exile.database.data.stats.types.resources.mana;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ManaBurn extends Stat {
    public static String GUID = "mana_burn";

    private ManaBurn() {
        this.scaling = StatScaling.NORMAL;
        this.statEffect = new Effect();
    }

    public static ManaBurn getInstance() {
        return ManaBurn.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Burns mana on basic attack";
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
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Mana Burn";
    }

    private static class Effect extends BaseDamageEffect {

        @Override
        public int GetPriority() {
            return Priority.Second.priority;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            effect.manaBurn = data.getAverageValue();
            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.getAttackType()
                .equals(AttackType.ATTACK);
        }
    }

    private static class SingletonHolder {
        private static final ManaBurn INSTANCE = new ManaBurn();
    }
}
