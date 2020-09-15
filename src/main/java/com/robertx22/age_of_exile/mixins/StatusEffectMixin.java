package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_ducks.StatusEffectAccesor;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StatusEffect.class)
public abstract class StatusEffectMixin implements StatusEffectAccesor {

    @Accessor(value = "type")
    @Override
    public abstract StatusEffectType my$getstatusEffectType();
}
