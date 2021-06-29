package com.robertx22.divine_missions_addon.events;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;

public class IsMobKilledValid extends EventConsumer<ExileEvents.IsEntityKilledValid> {

    @Override
    public void accept(ExileEvents.IsEntityKilledValid event) {

        int diff = Math.abs(Load.Unit(event.killer)
            .getLevel() - Load.Unit(event.mob)
            .getLevel());

        if (diff > ModConfig.get().Server.LEVELS_NEEDED_TO_START_LVL_PENALTY) {
            event.isValid = false;
        }

    }
}
