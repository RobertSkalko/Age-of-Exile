package com.robertx22.mine_and_slash.mixin_methods;

import com.robertx22.mine_and_slash.event_hooks.entity.damage.LivingHurtUtils;
import com.robertx22.mine_and_slash.uncommon.effectdatas.LivingHurtEvent;

public class OnHurtEvent {

    public static float onHurtEvent(LivingHurtEvent event) {

        if (event.getEntityLiving().world.isClient) {
            return event.getAmount();
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

        return event.getAmount();

    }

}
