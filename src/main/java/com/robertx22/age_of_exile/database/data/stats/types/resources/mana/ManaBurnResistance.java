package com.robertx22.age_of_exile.database.data.stats.types.resources.mana;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class ManaBurnResistance extends Stat {
    public static String GUID = "mana_burn_resist";

    private ManaBurnResistance() {
        this.scaling = StatScaling.SLOW;
        this.statEffect = new Effect();
    }

    public static ManaBurnResistance getInstance() {
        return ManaBurnResistance.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Protects against mana burn by x percent";
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
        return "Mana Burn Resist";
    }

    private static class Effect extends BaseDamageEffect {

        @Override
        public int GetPriority() {
            return Priority.afterThis(ManaBurn.getInstance().statEffect.GetPriority());
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Target;
        }

        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            if (effect.manaBurn > 0) {
                effect.manaBurn *= data.getReverseMultiplier();
            }
            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return true;
        }

    }

    private static class SingletonHolder {
        private static final ManaBurnResistance INSTANCE = new ManaBurnResistance();
    }
}

