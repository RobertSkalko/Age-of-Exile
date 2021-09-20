package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.world.server.ServerWorld;

public class OnTickDungeonWorld {

    
    public static void onEndTick(ServerWorld world) {

        if (WorldUtils.isMapWorldClass(world)) {
            Load.dungeonData(world).data.onTick(world);
        }
    }
}
