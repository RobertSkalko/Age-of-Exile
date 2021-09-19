package com.robertx22.age_of_exile.database.data.exile_effects;

public enum EffectType {
    beneficial(net.minecraft.potion.EffectType.BENEFICIAL),
    negative(net.minecraft.potion.EffectType.HARMFUL),
    buff(net.minecraft.potion.EffectType.BENEFICIAL),
    neutral(net.minecraft.potion.EffectType.NEUTRAL);

    public net.minecraft.potion.EffectType type;

    EffectType(net.minecraft.potion.EffectType type) {
        this.type = type;
    }
}