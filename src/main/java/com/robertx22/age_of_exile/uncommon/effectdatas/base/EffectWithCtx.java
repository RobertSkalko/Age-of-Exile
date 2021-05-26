package com.robertx22.age_of_exile.uncommon.effectdatas.base;

import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;

import java.util.Comparator;

public class EffectWithCtx implements Comparator<EffectWithCtx> {

    public static EffectWithCtx COMPARATOR = new EffectWithCtx();

    public IStatEffect effect;
    public EffectSides statSource;
    public StatData stat;

    public EffectWithCtx(IStatEffect effect, EffectSides statSource, StatData stat) {
        this.effect = effect;
        this.statSource = statSource;
        this.stat = stat;
    }

    public EffectWithCtx() {

    }

    @Override
    public int compare(EffectWithCtx arg0, EffectWithCtx arg1) {
        if (arg0.effect.GetPriority() == arg1.effect.GetPriority()) {
            return 0;
        } else if (arg0.effect.GetPriority() > arg1.effect.GetPriority()) {
            return 1;
        } else {
            return -1;
        }
    }

}
