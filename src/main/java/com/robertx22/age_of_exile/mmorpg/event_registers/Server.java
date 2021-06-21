package com.robertx22.age_of_exile.mmorpg.event_registers;

import com.robertx22.age_of_exile.event_hooks.player.OnLogin;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;

public class Server {

    public static void register() {

        ExileEvents.ON_PLAYER_LOGIN.register(new EventConsumer<ExileEvents.OnPlayerLogin>() {
            @Override
            public void accept(ExileEvents.OnPlayerLogin event) {
                OnLogin.onLoad(event.player);
            }
        });

    }
}
