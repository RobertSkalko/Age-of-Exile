package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;

public class OnTickDungeonWorld implements ServerTickEvents.EndWorldTick {

    @Override
    public void onEndTick(ServerWorld world) {

        if (WorldUtils.isDungeonWorld(world)) {
            Load.dungeonData(world).data.onTick(world);
        }
    }
}
