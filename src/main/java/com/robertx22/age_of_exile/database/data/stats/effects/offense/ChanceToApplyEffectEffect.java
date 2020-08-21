package com.robertx22.age_of_exile.database.data.stats.effects.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.PotionEffectUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ChanceToApplyEffectEffect extends BaseStatEffect<DamageEffect> {

    BasePotionEffect statusEffect;
    Set<EffectData.EffectTypes> onEffectType;

    public ChanceToApplyEffectEffect(BasePotionEffect effect, EffectData.EffectTypes... onEffectType) {
        super(DamageEffect.class);
        this.statusEffect = effect;
        this.onEffectType = new HashSet<>(Arrays.asList(onEffectType));
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
        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return onEffectType.contains(effect.getEffectType());
    }
}