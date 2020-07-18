package com.robertx22.mine_and_slash.event_hooks.entity.damage;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OnHurtEvent {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHurtEvent(LivingHurtEvent event) {

        if (event.getEntity().world.isClient) {
            return;
        }

        try {

            // order matters here
            LivingHurtUtils.onAttack(event);
            LivingHurtUtils.modifyDamage(event);
            LivingHurtUtils.onHurtRecordNonPlayerDmg(event);
            // order matters here

            LivingHurtUtils.damageCurioItems(event.getEntityLiving());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
