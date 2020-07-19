package com.robertx22.mine_and_slash.event_hooks.world;

import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;

public class WorldTickMinute implements ServerTickEvents.EndWorldTick {

    static int ticks = 0;

    @Override
    public void onEndTick(ServerWorld serverWorld) {
        ticks++;

        if (ticks > 20 * 60) {
            ticks = 0;
            Load.antiMobFarm(serverWorld)
                .onMinutePassed();
        }
    }
}
