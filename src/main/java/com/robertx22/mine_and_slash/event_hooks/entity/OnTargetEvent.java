package com.robertx22.mine_and_slash.event_hooks.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OnTargetEvent {

    @SubscribeEvent
    public static void onTarget(LivingSetAttackTargetEvent event) {

        try {

            if (true) {
                return;
            }

            if (event.getTarget() == null || event.getEntityLiving() == null) {
                return;
            }

            LivingEntity en = event.getEntityLiving();
            if (en instanceof MobEntity) {
                LivingEntity target = event.getTarget();
                MobEntity mob = (MobEntity) en;

                //if (target.getActivePotionEffect(StealthEffect.getInstance()) != null) {
                //   mob.setAttackTarget(null);
                //}

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
