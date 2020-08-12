package com.robertx22.age_of_exile.database.data.stats.effects.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.PotionEffectUtils;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

public class ChanceToApplyEffectEffect extends BaseStatEffect<DamageEffect> {

    BasePotionEffect statusEffect;

    public ChanceToApplyEffectEffect(BasePotionEffect effect) {
        super(DamageEffect.class);
        this.statusEffect = effect;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public int GetPriority() {
        return Priority.First.priority;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {

        if (RandomUtils.roll(data.getAverageValue())) {
            PotionEffectUtils.apply(this.statusEffect, effect.source, effect.target);
        }

        return null;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return effect.getEffectType() == EffectData.EffectTypes.BASIC_ATTACK;
    }
}