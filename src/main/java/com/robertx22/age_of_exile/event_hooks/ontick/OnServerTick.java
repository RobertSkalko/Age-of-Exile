package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.capability.bases.CapSyncUtil;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.dimension.PopulateDungeonChunks;
import com.robertx22.age_of_exile.dimension.dungeon_data.DungeonType;
import com.robertx22.age_of_exile.dimension.rules.OnTickSetGameMode;
import com.robertx22.age_of_exile.dimension.teleporter.TeleportedBlockEntity;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.*;
import com.robertx22.age_of_exile.vanilla_mc.packets.SyncAreaLevelPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellClientEntityIsCastingSpellPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class OnServerTick implements ServerTickEvents.EndTick {

    static List<PlayerTickAction> TICK_ACTIONS = new ArrayList<>();

    static {

        TICK_ACTIONS.add(new PlayerTickAction("update_caps", 18, (player, data) -> {

            OnTickSetGameMode.onTick(player);
            CapSyncUtil.syncAll(player);
            Packets.sendToClient(player, new SyncAreaLevelPacket(LevelUtils.determineLevel(player.world, player.getBlockPos(), player)));
        }));

        TICK_ACTIONS.add(new

            PlayerTickAction("second_pass", 20, (player, data) ->

        {
            UnequipGear.onTick(player);
            Load.spells(player)
                .getCastingData().charges.onTicks(player, 20);
        }));

        TICK_ACTIONS.add(new

            PlayerTickAction("regen", 60, (player, data) ->

        {

            if (false && MMORPG.RUN_DEV_TOOLS) {
                BlockPos pos = player.getBlockPos();
                World world = player.getServerWorld();

                if (world.isAir(pos)) {

                    world.setBlockState(pos, ModRegistry.BLOCKS.TELEPORTER.getDefaultState());
                    TeleportedBlockEntity be = (TeleportedBlockEntity) world.getBlockEntity(pos);
                    be.data.dungeon_type = DungeonType.RIFT;
                    be.data.rift_data.dun_type = DungeonType.RIFT;

                }// TODO
            }

            if (player.isAlive()) {

                PopulateDungeonChunks.tryPopulateChunksAroundPlayer(player.world, player);

                EntityCap.UnitData unitdata = Load.Unit(player);

                unitdata.getResources()
                    .shields.onTicksPassed(60);

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
        }));

        TICK_ACTIONS.add(new

            PlayerTickAction("compat_items", 40, (player, data) ->

        {
            CompatibleItemUtils.checkAndGenerate(player);
        }));

        TICK_ACTIONS.add(new

            PlayerTickAction("every_tick", 1, (player, data) ->

        {
            if (player.isBlocking()) {
                if (Load.spells(player)
                    .getCastingData()
                    .isCasting()) {
                    Load.spells(player)
                        .getCastingData()
                        .cancelCast(player);
                }
            }

            Load.spells(player)
                .getCastingData()
                .onTimePass(player, Load.spells(player), 1);

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
        }));

        TICK_ACTIONS.add(new

            PlayerTickAction("level_warning", 200, (player, data) ->

        {

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
            }
        }));

    }

    public static HashMap<UUID, PlayerTickData> PlayerTickDatas = new HashMap<UUID, PlayerTickData>();

    @Override
    public void onEndTick(MinecraftServer server) {

        for (ServerPlayerEntity player : server.getPlayerManager()
            .getPlayerList()) {

            try {

                PlayerTickData data = PlayerTickDatas.get(player.getUuid());

                if (data == null) {
                    data = new PlayerTickData();
                }

                data.tick(player);

                PlayerTickDatas.put(player.getUuid(), data);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static class PlayerTickData {

        HashMap<String, Integer> ticks = new HashMap<>();

        public boolean isInHighLvlZone = false;

        public void tick(ServerPlayerEntity player) {
            TICK_ACTIONS.forEach(x -> {
                x.tick(player, this);
            });
        }
    }

    public static class PlayerTickAction {

        public final String name;
        public final int ticksNeeded;
        private final BiConsumer<ServerPlayerEntity, PlayerTickData> action;

        public PlayerTickAction(String name, int ticksNeeded, BiConsumer<ServerPlayerEntity, PlayerTickData> action) {
            this.ticksNeeded = ticksNeeded;
            this.name = name;
            this.action = action;
        }

        public void tick(ServerPlayerEntity player, PlayerTickData data) {
            int ticks = data.ticks.getOrDefault(name, 0);
            ticks++;

            if (ticks % ticksNeeded == 0) {
                ticks = 0;
                action.accept(player, data);
            }
            data.ticks.put(name, ticks);

        }

    }

}
