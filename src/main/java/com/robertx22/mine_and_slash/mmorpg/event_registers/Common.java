package com.robertx22.mine_and_slash.mmorpg.event_registers;

import com.robertx22.mine_and_slash.a_libraries.curios.OnCurioChangeEvent;
import com.robertx22.mine_and_slash.event_hooks.entity.OnMobSpawn;
import com.robertx22.mine_and_slash.event_hooks.entity.OnTrackEntity;
import com.robertx22.mine_and_slash.event_hooks.ontick.OnServerTick;
import com.robertx22.mine_and_slash.event_hooks.player.OnLogin;
import com.robertx22.mine_and_slash.event_hooks.player.StopCastingIfInteract;
import com.robertx22.mine_and_slash.event_hooks.world.WorldTickMinute;
import nerdhub.cardinal.components.api.event.TrackingStartCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import top.theillusivec4.curios.api.event.CurioChangeCallback;

public class Common {

    public static void register() {

        AttackEntityCallback.EVENT.register(new StopCastingIfInteract());
        CurioChangeCallback.EVENT.register(new OnCurioChangeEvent());

        TrackingStartCallback.EVENT.register(new OnTrackEntity());
        ServerEntityEvents.ENTITY_LOAD.register(new OnMobSpawn());
        ServerEntityEvents.ENTITY_LOAD.register(new OnLogin());
        ServerTickEvents.END_SERVER_TICK.register(new OnServerTick());
        ServerTickEvents.END_WORLD_TICK.register(new WorldTickMinute());

    }

}
