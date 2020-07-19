package com.robertx22.mine_and_slash.uncommon.effectdatas;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class LivingHurtEvent {

    boolean canceled = false;
    LivingEntity sourceEntity;
    LivingEntity targetEntity;
    DamageSource dmgSource;
    float amount;

    public DamageSource getSource() {
        return dmgSource;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float f) {
        amount = f;
    }

    public LivingEntity getEntityLiving() {
        return targetEntity;
    }

    public void setCanceled(boolean c) {
        this.canceled = c;
    }
}
