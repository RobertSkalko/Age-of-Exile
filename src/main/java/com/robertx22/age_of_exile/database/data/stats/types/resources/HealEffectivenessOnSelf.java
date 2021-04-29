package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseHealEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.HealEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class HealEffectivenessOnSelf extends Stat {
    public static String GUID = "heal_effect_on_self";

    private HealEffectivenessOnSelf() {
        this.statGroup = StatGroup.RESTORATION;
        this.statEffect = new Effect();
    }

    public static HealEffectivenessOnSelf getInstance() {
        return HealEffectivenessOnSelf.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Affects all heal effects applied to you.";
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
        return "Heal Effectiveness";
    }

    private static class Effect extends BaseHealEffect {

        @Override
        public int GetPriority() {
            return Priority.First.priority;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Target;
        }

        @Override
        public HealEffect activate(HealEffect effect, StatData data, Stat stat) {
            effect.data.getNumber(EventData.NUMBER).number *= data.getMultiplier();
            return effect;
        }

        @Override
        public boolean canActivate(HealEffect effect, StatData data, Stat stat) {
            return true;
        }

    }

    private static class SingletonHolder {
        private static final HealEffectivenessOnSelf INSTANCE = new HealEffectivenessOnSelf();
    }
}


