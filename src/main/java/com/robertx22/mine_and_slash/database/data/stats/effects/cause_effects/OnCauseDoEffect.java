package com.robertx22.mine_and_slash.database.data.stats.effects.cause_effects;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.causes.BaseCause;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;

public class OnCauseDoEffect implements IStatEffect {

    public OnCauseDoEffect(BaseCause cause, int chance, EffectSides whoGetsEffect, BaseCauseEffect causeEffect,
                           EffectSides side) {
        this.cause = cause;
        this.chance = chance;
        this.whoGetsEffect = whoGetsEffect;
        this.causeEffect = causeEffect;
        this.side = side;

    }

    private BaseCauseEffect causeEffect;
    private BaseCause cause;
    private int chance;
    public EffectSides whoGetsEffect;
    public EffectSides side;

    @Override
    public int GetPriority() {
        return Priority.Last.priority + 1;
    } // TODO might be problematic

    @Override
    public EffectSides Side() {
        return EffectSides.Target;
    }

    @Override
    public EffectData TryModifyEffect(EffectData Effect, Unit source, StatData data, Stat stat) {

        try {
            if (RandomUtils.roll(chance)) {
                if (cause.shouldActivate(Effect)) {
                    causeEffect.activate(this, Effect);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Effect;
    }

}

