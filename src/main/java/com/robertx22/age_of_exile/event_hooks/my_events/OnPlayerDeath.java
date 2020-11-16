package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.capability.player.PlayerDeathData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.main.Packets;

public class OnPlayerDeath extends EventConsumer<ExileEvents.OnPlayerDeath> {

    @Override
    public void accept(ExileEvents.OnPlayerDeath event) {
        try {

            Load.Unit(event.player)
                .onDeath(event.player);

            Load.Unit(event.player)
                .setEquipsChanged(true);

            PlayerDeathData data = ModRegistry.COMPONENTS.PLAYER_DEATH_DATA.get(event.player);
            data.deathStats.died = true;

            Packets.sendToClient(event.player, new SyncCapabilityToClient(event.player, PlayerCaps.DEATH_STATS));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

