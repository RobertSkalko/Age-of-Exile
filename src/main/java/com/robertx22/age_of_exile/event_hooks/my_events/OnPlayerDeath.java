package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.dimension.dungeon_data.SingleDungeonData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;

public class OnPlayerDeath extends EventConsumer<ExileEvents.OnPlayerDeath> {

    @Override
    public void accept(ExileEvents.OnPlayerDeath event) {
        try {

            Load.Unit(event.player)
                .onDeath();

            Load.Unit(event.player)
                .setEquipsChanged(true);

            RPGPlayerData data = Load.playerRPGData(event.player);
            data.deathStats.died = true;

            data.syncToClient(event.player);

            Load.spells(event.player)
                .syncToClient(event.player);

            if (WorldUtils.isMapWorldClass(event.player.level)) {

                SingleDungeonData dungeon = Load.dungeonData(event.player.level).data.get(event.player.blockPosition());

                if (!dungeon.data.isEmpty()) {
                    dungeon.data.dun_type.onDeath(dungeon.data, event.player);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

