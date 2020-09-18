package com.robertx22.age_of_exile.mmorpg.event_registers;

import com.robertx22.age_of_exile.a_libraries.curios.OnCurioChangeEvent;
import com.robertx22.age_of_exile.event_hooks.entity.OnMobSpawn;
import com.robertx22.age_of_exile.event_hooks.entity.OnTrackEntity;
import com.robertx22.age_of_exile.event_hooks.my_events.*;
import com.robertx22.age_of_exile.event_hooks.ontick.OnServerTick;
import com.robertx22.age_of_exile.event_hooks.ontick.WorldTickEvent;
import com.robertx22.age_of_exile.event_hooks.player.StopCastingIfInteract;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import nerdhub.cardinal.components.api.event.TrackingStartCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import top.theillusivec4.curios.api.event.CurioChangeCallback;

public class CommonEvents {

    public static void register() {

        AttackEntityCallback.EVENT.register(new StopCastingIfInteract());
        CurioChangeCallback.EVENT.register(new OnCurioChangeEvent());

        TrackingStartCallback.EVENT.register(new OnTrackEntity());
        ServerEntityEvents.ENTITY_LOAD.register(new OnMobSpawn());
        ServerTickEvents.END_SERVER_TICK.register(new OnServerTick());

        ExileEvents.LIVING_ENTITY_TICK.register(new OnEntityTick());
        ExileEvents.MOB_DEATH.register(new OnMobDeathDrops());

        ExileEvents.DAMAGE_BEFORE_CALC.register(new OnNonPlayerDamageEntityEvent());
        ExileEvents.DAMAGE_AFTER_CALC.register(new OnPlayerDamageEntityEvent());
        ExileEvents.DAMAGE_AFTER_CALC.register(new OnDamageTryActivatePassiveSpells());

        ServerTickEvents.END_WORLD_TICK.register(new WorldTickEvent());
    }

}
