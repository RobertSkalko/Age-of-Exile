package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;

public abstract class InCodeStatEffect<T extends EffectEvent> implements IStatEffect {

    Class<T> theclass;

    public InCodeStatEffect(Class<T> theclass) {
        this.theclass = theclass;
    }

    public abstract T activate(T effect, StatData data, Stat stat);

    public abstract boolean canActivate(T effect, StatData data, Stat stat);

    public Unit getSource(EffectEvent effect) {
        if (Side() == EffectSides.Target) {
            return effect.targetData.getUnit();
        } else {
            return effect.sourceData.getUnit();
        }
    }

    @Override
    public final void TryModifyEffect(EffectEvent effect, EffectSides statSource, StatData data, Stat stat) {

        try {
            if (!effect.data.isCanceled()) {
                if (theclass.isAssignableFrom(effect.getClass())) {
                    if (canActivate((T) effect, data, stat)) {

                        activate((T) effect, data, stat);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
