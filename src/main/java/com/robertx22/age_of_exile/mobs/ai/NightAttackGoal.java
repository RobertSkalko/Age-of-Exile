package com.robertx22.age_of_exile.mobs.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

// copy this from spider
public class NightAttackGoal extends MeleeAttackGoal {

    public NightAttackGoal(PathAwareEntity mob, double speed) {
        super(mob, speed, true);
    }

    @Override
    public boolean canStart() {
        return super.canStart() && !this.mob.hasPassengers();
    }

    @Override
    public boolean shouldContinue() {
        float f = this.mob.getBrightnessAtEyes();
        if (f >= 0.5F && this.mob.getRandom()
            .nextInt(100) == 0) {
            this.mob.setTarget((LivingEntity) null);
            return false;
        } else {
            return super.shouldContinue();
        }
    }

    protected double getSquaredMaxAttackDistance(LivingEntity entity) {
        return (double) (4.0F + entity.getWidth());
    }
}