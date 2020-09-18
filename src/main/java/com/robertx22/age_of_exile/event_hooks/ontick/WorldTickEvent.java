package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.capability.world.WorldAreas;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;

public class WorldTickEvent implements ServerTickEvents.EndWorldTick {

    Boolean isday = null;

    @Override
    public void onEndTick(ServerWorld world) {

        try {
            if (isday == null) {
                isday = world.isDay();
                return;
            }

            if (isday == false && isday != world.isDay()) {
                // on dawn of new day
                WorldAreas.clear(world);
            }
            isday = world.isDay();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
