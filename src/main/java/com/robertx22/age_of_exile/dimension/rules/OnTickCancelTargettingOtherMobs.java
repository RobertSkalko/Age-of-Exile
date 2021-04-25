package com.robertx22.age_of_exile.dimension.rules;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public class OnTickCancelTargettingOtherMobs {
    public static void cancelTarget(MobEntity mob) {
        if (mob.age % 50 == 0) {
            if (mob.getTarget() instanceof PlayerEntity == false) {
                mob.setTarget(null);
            }
        }
    }
}
