package com.robertx22.age_of_exile.mmorpg.event_registers;

import com.robertx22.addon.divine_missions.events.IsMobKilledValid;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.damage_hooks.*;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.event_hooks.entity.OnMobSpawn;
import com.robertx22.age_of_exile.event_hooks.entity.OnTrackEntity;
import com.robertx22.age_of_exile.event_hooks.my_events.OnEntityTick;
import com.robertx22.age_of_exile.event_hooks.my_events.OnLootChestEvent;
import com.robertx22.age_of_exile.event_hooks.my_events.OnMobDeathDrops;
import com.robertx22.age_of_exile.event_hooks.my_events.OnPlayerDeath;
import com.robertx22.age_of_exile.event_hooks.ontick.OnServerTick;
import com.robertx22.age_of_exile.event_hooks.ontick.OnTickDungeonWorld;
import com.robertx22.age_of_exile.event_hooks.player.OnLogin;
import com.robertx22.age_of_exile.mixin_methods.OnItemStoppedUsingCastImbuedSpell;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.error_checks.base.ErrorChecks;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.main.ForgeEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;

public class CommonEvents {

    public static void register() {

        OnItemStoppedUsingCastImbuedSpell.register();

        ForgeEvents.registerForgeEvent(EntityJoinWorldEvent.class, event -> {
            OnMobSpawn.onLoad(event.getEntity());
        });

        ForgeEvents.registerForgeEvent(TickEvent.ServerTickEvent.class, event -> {
            if (event.phase == TickEvent.Phase.END) {
                OnServerTick.onEndTick(ServerLifecycleHooks.getCurrentServer());
            }
        });

        ForgeEvents.registerForgeEvent(TickEvent.WorldTickEvent.class, event -> {
            if (event.phase == TickEvent.Phase.END && event.world instanceof ServerWorld) {
                OnTickDungeonWorld.onEndTick((ServerWorld) event.world);
            }
        });

        ForgeEvents.registerForgeEvent(PlayerEvent.StartTracking.class, event -> {
            if (event.getPlayer() instanceof ServerPlayerEntity) {
                OnTrackEntity.onPlayerStartTracking((ServerPlayerEntity) event.getPlayer(), event.getTarget());
            }
        });

        ExileEvents.IS_KILLED_ENTITY_VALID.register(new IsMobKilledValid());
        ExileEvents.ON_CHEST_LOOTED.register(new OnLootChestEvent());
        ExileEvents.LIVING_ENTITY_TICK.register(new OnEntityTick());
        ExileEvents.MOB_DEATH.register(new OnMobDeathDrops());

        ExileEvents.DAMAGE_BEFORE_CALC.register(new OnNonPlayerDamageEntityEvent());

        ExileEvents.DAMAGE_BEFORE_CALC.register(new ScaleVanillaMobDamage());
        ExileEvents.DAMAGE_BEFORE_CALC.register(new ScaleVanillaPlayerDamage());

        ExileEvents.DAMAGE_AFTER_CALC.register(new OnPlayerDamageEntityEvent());

        ExileEvents.PLAYER_DEATH.register(new OnPlayerDeath());

        ForgeEvents.registerForgeEvent(LivingHurtEvent.class, event -> {
            // reduce enviro dmg based on total hp from formula
            try {
                if (event.getEntityLiving() instanceof PlayerEntity) {
                    if (LivingHurtUtils.isEnviromentalDmg(event.getSource())) {
                        EntityData data = Load.Unit(event.getEntityLiving());
                        float reduction = Health.getInstance()
                            .getUsableValue((int) data.getUnit()
                                .healthData()
                                .getValue(), data.getLevel());

                        float multi = 1 - reduction;

                        event.setAmount(event.getAmount() * multi);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        ExileEvents.PLAYER_DEATH.register(new EventConsumer<ExileEvents.OnPlayerDeath>() {
            @Override
            public void accept(ExileEvents.OnPlayerDeath event) {
                if (event.player instanceof ServerPlayerEntity) {
                    try {
                        RPGPlayerData data = Load.playerRPGData(event.player);

                        data.syncToClient(event.player);

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
