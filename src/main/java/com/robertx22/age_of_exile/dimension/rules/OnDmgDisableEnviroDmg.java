package com.robertx22.age_of_exile.dimension.rules;

import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.player.PlayerEntity;

public class OnDmgDisableEnviroDmg {

    public static void accept(ExileEvents.OnDamageEntity event) {
        if (event.mob instanceof PlayerEntity == false) {
            if (WorldUtils.isDungeonWorld(event.mob.world)) {
                event.damage = 0;
            }
        }
    }
}
