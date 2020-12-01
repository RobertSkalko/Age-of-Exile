package com.robertx22.age_of_exile.mmorpg.event_registers;

import com.robertx22.age_of_exile.a_libraries.curios.OnCurioChangeEvent;
import com.robertx22.age_of_exile.damage_hooks.OnNonPlayerDamageEntityEvent;
import com.robertx22.age_of_exile.damage_hooks.OnPlayerDamageEntityEvent;
import com.robertx22.age_of_exile.damage_hooks.ScaleVanillaMobDamage;
import com.robertx22.age_of_exile.damage_hooks.ScaleVanillaPlayerDamage;
import com.robertx22.age_of_exile.event_hooks.entity.OnMobSpawn;
import com.robertx22.age_of_exile.event_hooks.entity.OnTrackEntity;
import com.robertx22.age_of_exile.event_hooks.my_events.OnEntityTick;
import com.robertx22.age_of_exile.event_hooks.my_events.OnMobDeathDrops;
import com.robertx22.age_of_exile.event_hooks.my_events.OnPlayerDeath;
import com.robertx22.age_of_exile.event_hooks.ontick.OnServerTick;
import com.robertx22.age_of_exile.event_hooks.ontick.WorldTickEvent;
import com.robertx22.age_of_exile.event_hooks.player.OnDamagePlayerActivateTablets;
import com.robertx22.age_of_exile.event_hooks.player.StopCastingIfInteract;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import nerdhub.cardinal.components.api.event.TrackingStartCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.server.network.ServerPlayerEntity;
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

        ExileEvents.DAMAGE_BEFORE_CALC.register(new ScaleVanillaMobDamage());
        ExileEvents.DAMAGE_BEFORE_CALC.register(new ScaleVanillaPlayerDamage());

        ExileEvents.DAMAGE_AFTER_CALC.register(new OnPlayerDamageEntityEvent());
        ExileEvents.DAMAGE_AFTER_CALC.register(new OnDamagePlayerActivateTablets());

        ExileEvents.PLAYER_DEATH.register(new OnPlayerDeath());

        ServerTickEvents.END_WORLD_TICK.register(new WorldTickEvent());

        ExileEvents.PLAYER_DEATH.register(new EventConsumer<ExileEvents.OnPlayerDeath>() {
            @Override
            public void accept(ExileEvents.OnPlayerDeath event) {
                if (event.player instanceof ServerPlayerEntity) {
                    try {
                        ModRegistry.COMPONENTS.PLAYER_DEATH_DATA.get(event.player).deathPos = event.player.getBlockPos();
                        ModRegistry.COMPONENTS.PLAYER_DEATH_DATA.get(event.player).deathDim = event.player.world.getRegistryKey()
                            .getValue()
                            .toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

}
