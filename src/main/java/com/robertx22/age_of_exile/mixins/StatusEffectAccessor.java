package com.robertx22.age_of_exile.mixins;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Effect.class)
public interface StatusEffectAccessor {
    @Accessor
    EffectType getType();
}
