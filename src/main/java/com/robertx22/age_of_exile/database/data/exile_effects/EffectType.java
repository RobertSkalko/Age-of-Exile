package com.robertx22.age_of_exile.database.data.exile_effects;

import net.minecraft.entity.effect.StatusEffectType;

public enum EffectType {
    beneficial(StatusEffectType.BENEFICIAL),
    negative(StatusEffectType.HARMFUL),
    buff(StatusEffectType.BENEFICIAL),
    neutral(StatusEffectType.NEUTRAL);

    public StatusEffectType type;

    EffectType(StatusEffectType type) {
        this.type = type;
    }
}