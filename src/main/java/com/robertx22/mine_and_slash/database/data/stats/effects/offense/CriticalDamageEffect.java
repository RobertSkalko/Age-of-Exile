package com.robertx22.mine_and_slash.database.data.stats.effects.offense;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseAnyEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.ICrittable;

public class CriticalDamageEffect extends BaseAnyEffect {

    @Override
    public int GetPriority() {
        return Priority.afterThis(new CriticalHitEffect().GetPriority());
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public EffectData activate(EffectData effect, StatData data, Stat stat) {
        effect.number *= data.getMultiplier();
        return effect;
    }

    @Override
    public boolean canActivate(EffectData effect, StatData data, Stat stat) {
        return effect instanceof ICrittable && ((ICrittable) effect).isCriticalHit();
    }

}
