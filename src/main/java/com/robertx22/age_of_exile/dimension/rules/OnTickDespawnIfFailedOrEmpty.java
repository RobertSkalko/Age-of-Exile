package com.robertx22.age_of_exile.dimension.rules;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.mob.MobEntity;

public class OnTickDespawnIfFailedOrEmpty {
    public static void despawn(MobEntity mob) {
        if (mob.age % 50 == 0) {
            try {
                if (Load.dungeonData(mob.world).data.get(mob.getBlockPos()).data.failedOrEmpty()) {
                    mob.kill();
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
