package com.robertx22.age_of_exile.database.data.exile_effects;

import net.minecraft.entity.effect.StatusEffectType;

public enum EffectType {
    BENEFICIAL(StatusEffectType.BENEFICIAL),
    HARMFUL(StatusEffectType.HARMFUL),
    BUFF(StatusEffectType.BENEFICIAL),
    NEUTRAL(StatusEffectType.NEUTRAL);

    public StatusEffectType type;

    EffectType(StatusEffectType type) {
        this.type = type;
    }
}