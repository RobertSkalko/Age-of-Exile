package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.event_hooks.entity.damage.LivingHurtUtils;
import com.robertx22.age_of_exile.uncommon.effectdatas.LivingHurtEvent;

public class OnHurtEvent {

    public static float onHurtEvent(LivingHurtEvent event) {

        if (event.getEntityLiving().world.isClient) {
            return event.getAmount();
        }

        try {

            // order matters here
            LivingHurtUtils.onAttack(event);
            LivingHurtUtils.onHurtRecordNonPlayerDmg(event);
            // order matters here

        } catch (Exception e) {
            e.printStackTrace();
        }

        return event.getAmount();

    }

}
