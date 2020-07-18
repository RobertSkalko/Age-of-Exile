package com.robertx22.mine_and_slash.database.data.stats.effects.resource;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;

public class MagicStealEffect extends BaseDamageEffect {

    private MagicStealEffect() {
    }

    public static MagicStealEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public int GetPriority() {
        return Priority.Last.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        float healed = ((float) data.getAverageValue() * effect.number / 100);

        effect.magicShieldRestored += healed;

        return effect;

    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return effect.getEffectType()
            .equals(EffectData.EffectTypes.BASIC_ATTACK);
    }

    private static class SingletonHolder {
        private static final MagicStealEffect INSTANCE = new MagicStealEffect();
    }
}

