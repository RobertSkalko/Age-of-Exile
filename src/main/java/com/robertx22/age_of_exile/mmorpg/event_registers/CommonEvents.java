package com.robertx22.age_of_exile.mmorpg.event_registers;

import com.robertx22.age_of_exile.a_libraries.curios.OnCurioChangeEvent;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.damage_hooks.OnNonPlayerDamageEntityEvent;
import com.robertx22.age_of_exile.damage_hooks.OnPlayerDamageEntityEvent;
import com.robertx22.age_of_exile.damage_hooks.ScaleVanillaMobDamage;
import com.robertx22.age_of_exile.damage_hooks.ScaleVanillaPlayerDamage;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.event_hooks.entity.OnMobSpawn;
import com.robertx22.age_of_exile.event_hooks.entity.OnTrackEntity;
import com.robertx22.age_of_exile.event_hooks.my_events.OnEntityTick;
import com.robertx22.age_of_exile.event_hooks.my_events.OnLootChestEvent;
import com.robertx22.age_of_exile.event_hooks.my_events.OnMobDeathDrops;
import com.robertx22.age_of_exile.event_hooks.my_events.OnPlayerDeath;
import com.robertx22.age_of_exile.event_hooks.ontick.OnServerTick;
import com.robertx22.age_of_exile.event_hooks.ontick.OnTickDungeonWorld;
import com.robertx22.age_of_exile.event_hooks.player.OnDamagePlayerActivateTablets;
import com.robertx22.age_of_exile.event_hooks.player.OnLogin;
import com.robertx22.age_of_exile.event_hooks.player.StopCastingIfInteract;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.error_checks.base.ErrorChecks;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import com.robertx22.divine_missions_addon.events.IsMobKilledValid;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import nerdhub.cardinal.components.api.event.TrackingStartCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.player.ServerPlayerEntity;
import org.apache.commons.lang3.tuple.ImmutablePair;
import top.theillusivec4.curios.api.event.CurioChangeCallback;

import java.util.ArrayList;

public class CommonEvents {

    public static void register() {

        AttackEntityCallback.EVENT.register(new StopCastingIfInteract());
        CurioChangeCallback.EVENT.register(new OnCurioChangeEvent());

        TrackingStartCallback.EVENT.register(new OnTrackEntity());
        ServerEntityEvents.ENTITY_LOAD.register(new OnMobSpawn());
        ServerTickEvents.END_SERVER_TICK.register(new OnServerTick());
        ServerTickEvents.END_WORLD_TICK.register(new OnTickDungeonWorld());

        ExileEvents.IS_KILLED_ENTITY_VALID.register(new IsMobKilledValid());
        ExileEvents.ON_CHEST_LOOTED.register(new OnLootChestEvent());
        ExileEvents.LIVING_ENTITY_TICK.register(new OnEntityTick());
        ExileEvents.MOB_DEATH.register(new OnMobDeathDrops());

        ExileEvents.DAMAGE_BEFORE_CALC.register(new OnNonPlayerDamageEntityEvent());

        ExileEvents.DAMAGE_BEFORE_CALC.register(new ScaleVanillaMobDamage());
        ExileEvents.DAMAGE_BEFORE_CALC.register(new ScaleVanillaPlayerDamage());

        ExileEvents.DAMAGE_AFTER_CALC.register(new OnPlayerDamageEntityEvent());
        ExileEvents.DAMAGE_AFTER_CALC.register(new OnDamagePlayerActivateTablets());

        ExileEvents.PLAYER_DEATH.register(new OnPlayerDeath());

        ExileEvents.PLAYER_DEATH.register(new EventConsumer<ExileEvents.OnPlayerDeath>() {
            @Override
            public void accept(ExileEvents.OnPlayerDeath event) {
                if (event.player instanceof ServerPlayerEntity) {
                    try {
                        RPGPlayerData data = Load.playerRPGData(event.player);

                        data.deathStats.deathPos = event.player.blockPosition();
                        data.deathStats.deathDim = event.player.level.dimension()
                            .location()
                            .toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ExileEvents.ON_PLAYER_LOGIN.register(new EventConsumer<ExileEvents.OnPlayerLogin>() {
            @Override
            public void accept(ExileEvents.OnPlayerLogin event) {
                OnLogin.onLoad(event.player);
            }
        });
        ExileEvents.AFTER_DATABASE_LOADED.register(new EventConsumer<ExileEvents.AfterDatabaseLoaded>() {
            @Override
            public void accept(ExileEvents.AfterDatabaseLoaded event) {
                Cached.reset();
                setupStatsThatAffectVanillaStatsList();
                ErrorChecks.getAll()
                    .forEach(x -> x.check());
            }
        });

    }

    private static void setupStatsThatAffectVanillaStatsList() {
        Cached.VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC = new ArrayList<>();

        ExileDB.Stats()
            .getFilterWrapped(x -> x instanceof AttributeStat).list.forEach(x -> {
                AttributeStat attri = (AttributeStat) x;
                Cached.VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC.add(ImmutablePair.of(attri.attribute, attri.uuid));
            });
    }
}
