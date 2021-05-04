package com.robertx22.age_of_exile.database.data.stats.types.resources.aura;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.InCodeStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.ReserveManaEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

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

    public static class Effect extends InCodeStatEffect<ReserveManaEvent> {

        private Effect() {
            super(ReserveManaEvent.class);
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
        public ReserveManaEvent activate(ReserveManaEvent effect, StatData data, Stat stat) {
            effect.data.getNumber(EventData.NUMBER).number *= data.getReverseMultiplier();
            return effect;
        }

        @Override
        public boolean canActivate(ReserveManaEvent effect, StatData data, Stat stat) {
            return true;
        }
    }

    private static class SingletonHolder {
        private static final ReducedManaReserved INSTANCE = new ReducedManaReserved();
    }
}
