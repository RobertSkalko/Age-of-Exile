package com.robertx22.mine_and_slash.mmorpg.registers;

import com.robertx22.mine_and_slash.event_hooks.entity.OnMobSpawn;
import com.robertx22.mine_and_slash.event_hooks.entity.OnTrackEntity;
import com.robertx22.mine_and_slash.event_hooks.ontick.OnServerTick;
import com.robertx22.mine_and_slash.event_hooks.player.OnLogin;
import com.robertx22.mine_and_slash.event_hooks.world.WorldTickMinute;
import nerdhub.cardinal.components.api.event.TrackingStartCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class Server {

    public static void register() {

        TrackingStartCallback.EVENT.register(new OnTrackEntity());
        ServerEntityEvents.ENTITY_LOAD.register(new OnMobSpawn());
        ServerEntityEvents.ENTITY_LOAD.register(new OnLogin());
        ServerTickEvents.END_SERVER_TICK.register(new OnServerTick());
        ServerTickEvents.END_WORLD_TICK.register(new WorldTickMinute());

    }
}
