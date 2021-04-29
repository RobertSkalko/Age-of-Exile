package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import net.minecraft.util.Formatting;

public abstract class BaseStatEffect<T extends EffectData> implements IStatEffect {

    Class<T> theclass;

    public BaseStatEffect(Class<T> theclass) {
        this.theclass = theclass;
    }

    public abstract T activate(T effect, StatData data, Stat stat);

    public abstract boolean canActivate(T effect, StatData data, Stat stat);

    public void log(String str) {
        if (MMORPG.RUN_DEV_TOOLS && MMORPG.statEffectDebuggingEnabled()) {
            System.out.println(Formatting.LIGHT_PURPLE + str);
        }
    }

    public Unit getSource(EffectData effect) {
        if (Side() == EffectSides.Target) {
            return effect.targetData.getUnit();
        } else {
            return effect.sourceData.getUnit();
        }
    }

    @Override
    public final EffectData TryModifyEffect(EffectData effect, Unit source, StatData data, Stat stat) {

        try {
            if (!effect.data.isCanceled()) {
                if (theclass.isAssignableFrom(effect.getClass())) {
                    if (canActivate((T) effect, data, stat)) {

                        activate((T) effect, data, stat);

                        return effect;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return effect;
    }
}
