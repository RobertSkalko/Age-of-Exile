package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;

public class OnPlayerDeath extends EventConsumer<ExileEvents.OnPlayerDeath> {

    @Override
    public void accept(ExileEvents.OnPlayerDeath event) {
        try {
            Load.Unit(event.player)
                .setEquipsChanged(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

