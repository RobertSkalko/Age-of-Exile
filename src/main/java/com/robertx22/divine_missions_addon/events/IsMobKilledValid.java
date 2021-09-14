package com.robertx22.divine_missions_addon.events;

import com.robertx22.age_of_exile.loot.LootUtils;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;

public class IsMobKilledValid extends EventConsumer<ExileEvents.IsEntityKilledValid> {

    @Override
    public void accept(ExileEvents.IsEntityKilledValid event) {

        if (LootUtils.preventLootDueToLevelPenalty(Load.Unit(event.mob)
            .getLevel(), Load.Unit(event.killer)
            .getLevel())) {
            event.isValid = false;
        }

    }
}
