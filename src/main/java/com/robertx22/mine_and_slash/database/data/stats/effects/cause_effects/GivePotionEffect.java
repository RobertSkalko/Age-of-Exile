package com.robertx22.mine_and_slash.database.data.stats.effects.cause_effects;

import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;
import com.robertx22.mine_and_slash.uncommon.interfaces.IStatEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class GivePotionEffect extends BaseCauseEffect {

    private StatusEffect potion;
    private int durationInSeconds;
    private int amplifier = 1;

    public GivePotionEffect(StatusEffect potion, int durationInSeconds) {
        this.potion = potion;
        this.durationInSeconds = durationInSeconds;

    }

    public GivePotionEffect setAmplifier(int amp) {
        this.amplifier = amp;
        return this;
    }

    @Override
    public void activate(OnCauseDoEffect oncause, EffectData effect) {

        LivingEntity entity;

        if (oncause.whoGetsEffect.equals(IStatEffect.EffectSides.Source)) {
            entity = effect.source;

        } else {
            entity = effect.target;
        }

        entity.addStatusEffect(new StatusEffectInstance(potion, durationInSeconds * 20, amplifier));

    }
}
