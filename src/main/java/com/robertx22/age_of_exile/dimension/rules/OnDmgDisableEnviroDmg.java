package com.robertx22.age_of_exile.dimension.rules;

import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.ExileEvents;

public class OnDmgDisableEnviroDmg {

    public static void accept(ExileEvents.OnDamageEntity event) {
        if (WorldUtils.isDungeonWorld(event.mob.world)) {
            event.damage = 0;
        }
    }
}
