package com.robertx22.age_of_exile.database.data.stats.types.resources.aura;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AuraStatEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class IncreasedEffectOfAuras extends Stat {

    public static String GUID = "inc_aura_effect";

    public static IncreasedEffectOfAuras getInstance() {
        return IncreasedEffectOfAuras.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases effect of auras.";
    }

    private IncreasedEffectOfAuras() {
        this.base_val = 0;
        this.min_val = -500;
        this.max_val = 500;
        this.scaling = StatScaling.NONE;
        this.statGroup = StatGroup.MAIN;
        this.statEffect = new Effect();

        this.calcOrder = CalcOrder.FIRST; // cus it affects the aura stats, needs to be first
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
        return "Aura Effect Strength";
    }

    public static class Effect extends BaseStatEffect<AuraStatEffect> {
// this is called when stats arent calculated yet, so stat is ALWAYS 0

        private Effect() {

            super(AuraStatEffect.class);
        }

        @Override
        public int GetPriority() {
            return Priority.First.priority;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public AuraStatEffect activate(AuraStatEffect effect, StatData data, Stat stat) {
            effect.aura_stat_multi += data.getMultiplier() - 1;
            return effect;
        }

        @Override
        public boolean canActivate(AuraStatEffect effect, StatData data, Stat stat) {
            return true;
        }
    }

    private static class SingletonHolder {
        private static final IncreasedEffectOfAuras INSTANCE = new IncreasedEffectOfAuras();
    }
}

