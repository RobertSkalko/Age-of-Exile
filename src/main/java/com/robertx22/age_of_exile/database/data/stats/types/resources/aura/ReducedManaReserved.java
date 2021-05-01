package com.robertx22.age_of_exile.database.data.stats.types.resources.aura;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.ReserveManaEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ReducedManaReserved extends Stat {

    public static String GUID = "red_mana_reserved";

    public static ReducedManaReserved getInstance() {
        return ReducedManaReserved.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Reduces mana reserved by auras.";
    }

    private ReducedManaReserved() {
        this.base = 0;
        this.min = 0;
        this.max = 75;
        this.scaling = StatScaling.NONE;
        this.group = StatGroup.Misc;
        this.statEffect = new Effect();
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
        return "Reduced Mana Reserved";
    }

    public static class Effect extends BaseStatEffect<ReserveManaEffect> {

        private Effect() {
            super(ReserveManaEffect.class);
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
        public ReserveManaEffect activate(ReserveManaEffect effect, StatData data, Stat stat) {
            effect.manaReserved *= data.getReverseMultiplier();
            return effect;
        }

        @Override
        public boolean canActivate(ReserveManaEffect effect, StatData data, Stat stat) {
            return true;
        }
    }

    private static class SingletonHolder {
        private static final ReducedManaReserved INSTANCE = new ReducedManaReserved();
    }
}
