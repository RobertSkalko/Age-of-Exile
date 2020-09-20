package com.robertx22.age_of_exile.vanilla_mc.potion_effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;

public interface IApplyStatPotion {

    void applyStats(World world, StatusEffectInstance instance, LivingEntity target);

}
