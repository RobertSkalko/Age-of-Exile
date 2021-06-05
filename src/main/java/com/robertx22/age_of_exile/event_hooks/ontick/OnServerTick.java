package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.capability.bases.CapSyncUtil;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.dimension.PopulateDungeonChunks;
import com.robertx22.age_of_exile.dimension.rules.OnTickSetGameMode;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.*;
import com.robertx22.age_of_exile.vanilla_mc.packets.SyncAreaLevelPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellClientEntityIsCastingSpellPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.EntityUtils;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class OnServerTick implements ServerTickEvents.EndTick {

    static final int TicksToUpdatePlayer = 18;
    static final int TicksForSecond = 20;
    static final int TicksToRegen = 60; // was 100, todo balance
    static final int TicksToPassMinute = 1200;
    static final int TicksToSpellCooldowns = 1;
    static final int TicksToCompatibleItems = 40;
    static final int TicksToLevelWarning = 200;

    public static HashMap<UUID, PlayerTickData> PlayerTickDatas = new HashMap<UUID, PlayerTickData>();

    public static Consumer<EnsureTeleportData> MAKE_SURE_TELEPORT = x -> {
        if (x.player.getBlockPos()
            .getSquaredDistance(x.whereShouldTeleport) > 1000) {

            if (x.tries > 5) {
                BlockPos spawnpos = x.player.getSpawnPointPosition();
                if (spawnpos != null) {
                    EntityUtils.setLoc(x.player, spawnpos);
                }
                x.cancel(); // pray that works at least
                return;
            } else {

                x.resetTicks();

                x.tries++;

                x.player.sendMessage(new LiteralText("There was a teleport bug but the auto correction system should have teleported you back correctly"), false);

                TeleportUtils.teleport(x.player, x.whereShouldTeleport);
            }
        }

    };

    public static void makeSureTeleport(ServerPlayerEntity player, BlockPos teleportPos, Identifier dim) {

        if (!PlayerTickDatas.containsKey(player.getUuid())) {
            PlayerTickDatas.put(player.getUuid(), new PlayerTickData());
        }

        PlayerTickDatas.get(player.getUuid()).ensureTeleportData = new EnsureTeleportData(player, MAKE_SURE_TELEPORT, 50, teleportPos);

        TeleportUtils.teleport(player, teleportPos, dim);

    }

    @Override
    public void onEndTick(MinecraftServer server) {

        for (ServerPlayerEntity player : server.getPlayerManager()
            .getPlayerList()) {

            try {

                PlayerTickData data = PlayerTickDatas.get(player.getUuid());

                if (data == null) {
                    data = new PlayerTickData();
                }

                if (data.ensureTeleportData != null && data.ensureTeleportData.ticks > 3) {
                    data.ensureTeleportData.action.accept(data.ensureTeleportData);
                    if (data.ensureTeleportData.ticksLeft < 1) {
                        data.ensureTeleportData = null;
                    }
                }

                if (player.isBlocking()) {
                    if (Load.spells(player)
                        .getCastingData()
                        .isCasting()) {
                        Load.spells(player)
                            .getCastingData()
                            .cancelCast(player);
                    }
                }

                Load.Unit(player)
                    .getResources()
                    .onTickBlock(player);

                Load.Unit(player)
                    .getCooldowns()
                    .onTicksPass(1);

                Spell spell = Load.spells(player)
                    .getCastingData()
                    .getSpellBeingCast();

                if (spell != null) {
                    SpellCastContext ctx = new SpellCastContext(player, 0, spell);
                    spell
                        .getAttached()
                        .tryActivate(Spell.CASTER_NAME, SpellCtx.onTick(player, player, EntitySavedSpellData.create(ctx.skillGemData.lvl, player, spell)));

                    PlayerStream.watching(player.world, player.getBlockPos())
                        .forEach((p) -> {
                            Packets.sendToClient(p, new TellClientEntityIsCastingSpellPacket(player, spell));
                        });

                }

                data.increment();

                if (data.regenTicks > TicksToRegen) {

                    data.regenTicks = 0;
                    if (player.isAlive()) {

                        PopulateDungeonChunks.tryPopulateChunksAroundPlayer(player.world, player);

                        EntityCap.UnitData unitdata = Load.Unit(player);

                        unitdata.getResources()
                            .shields.onTicksPassed(TicksToRegen);

                        unitdata.tryRecalculateStats();

                        RestoreResourceEvent mana = EventBuilder.ofRestore(player, player, ResourceType.mana, RestoreType.regen, 0)
                            .build();
                        mana.Activate();

                        boolean restored = false;

                        boolean canHeal = player.getHungerManager()
                            .getFoodLevel() >= 16;

                        if (canHeal) {
                            if (player.getHealth() < player.getMaxHealth()) {
                                restored = true;
                            }

                            RestoreResourceEvent hpevent = EventBuilder.ofRestore(player, player, ResourceType.health, RestoreType.regen, 0)
                                .build();
                            hpevent.Activate();

                            if (restored) {
                                unitdata.syncToClient(player);

                                float percentHealed = hpevent.data.getNumber() / HealthUtils.getMaxHealth(player);

                                float exhaustion = (float) ModConfig.get().Server.REGEN_HUNGER_COST * percentHealed;

                                player.getHungerManager()
                                    .addExhaustion(exhaustion);

                            }
                        }

                    }
                }

                if (data.ticksToPassMinute > TicksToPassMinute) {
                    data.ticksToPassMinute = 0;
                }

                if (data.ticksToCompItems > TicksToCompatibleItems) {
                    CompatibleItemUtils.checkAndGenerate(player);
                    data.ticksToCompItems = 0;

                }
                if (data.ticksToLvlWarning > TicksToLevelWarning) {

                    if (!WorldUtils.isDungeonWorld(player.world)) {

                        boolean wasnt = false;
                        if (!data.isInHighLvlZone) {
                            wasnt = true;
                        }

                        int lvl = Load.Unit(player)
                            .getLevel();

                        if (lvl < 20) {
                            data.isInHighLvlZone = LevelUtils.determineLevel(player.world, player.getBlockPos(), player) - lvl > 10;

                            if (wasnt && data.isInHighLvlZone) {
                                OnScreenMessageUtils.sendMessage(
                                    player,
                                    new LiteralText("YOU ARE ENTERING").formatted(Formatting.RED)
                                        .formatted(Formatting.BOLD),
                                    new LiteralText("A HIGH LEVEL ZONE").formatted(Formatting.RED)
                                        .formatted(Formatting.BOLD));
                            }
                        }
                        data.ticksToLvlWarning = 0;
                    }
                }

                if (data.ticksToSpellCooldowns >= TicksToSpellCooldowns) {
                    data.ticksToSpellCooldowns = 0;

                    Load.spells(player)
                        .getCastingData()
                        .onTimePass(player, Load.spells(player), TicksToSpellCooldowns);
                }

                if (data.ticksForSecond > TicksForSecond) {
                    data.ticksForSecond = 0;

                    Load.spells(player)
                        .getCastingData().charges.onTicks(player, 20);

                }

                if (data.playerSyncTick > TicksToUpdatePlayer) {
                    data.playerSyncTick = 0;

                    OnTickSetGameMode.onTick(player);

                    CapSyncUtil.syncAll(player);
                    Packets.sendToClient(player, new SyncAreaLevelPacket(LevelUtils.determineLevel(player.world, player.getBlockPos(), player)));
                }

                UnequipGear.onTick(player);

                PlayerTickDatas.put(player.getUuid(), data);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static class PlayerTickData {
        public int regenTicks = 0;
        public int playerSyncTick = 0;
        public int ticksToPassMinute = 0;
        public int ticksToSpellCooldowns = 0;
        public int ticksToCompItems = 0;
        public int ticksToLvlWarning = 0;
        public int ticksForSecond = 0;

        public EnsureTeleportData ensureTeleportData;

        public boolean isInHighLvlZone = false;

        public void increment() {
            if (ensureTeleportData != null) {
                ensureTeleportData.ticksLeft--;
                ensureTeleportData.ticks++;
            }
            ticksForSecond++;
            regenTicks++;
            playerSyncTick++;
            ticksToPassMinute++;
            ticksToCompItems++;
            ticksToSpellCooldowns++;
            ticksToLvlWarning++;

        }

    }

}
