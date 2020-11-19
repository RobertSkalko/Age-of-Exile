package com.robertx22.age_of_exile.mixin_ducks;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;

public interface LivingEntityAccesor {

    void myknockback(LivingEntity target);

    SoundEvent myGetHurtSound(DamageSource source);

    float myGetHurtVolume();

    float myGetHurtPitch();

}
