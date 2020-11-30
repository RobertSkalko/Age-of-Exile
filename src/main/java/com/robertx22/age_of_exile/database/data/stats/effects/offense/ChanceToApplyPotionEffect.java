package com.robertx22.age_of_exile.database.data.stats.effects.offense;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectsManager;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseStatEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ChanceToApplyPotionEffect extends BaseStatEffect<DamageEffect> {

    ExileEffect statusEffect;
    Set<AttackType> onEffectType;

    public ChanceToApplyPotionEffect(ExileEffect effect, AttackType... onEffectType) {
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
            ExileEffectsManager.apply(this.statusEffect, effect.source, effect.target, 20 * 10);
        }
        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return onEffectType.contains(effect.getAttackType());
    }
}