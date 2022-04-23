package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.capability.bases.CapSyncUtil;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.dimension.PopulateDungeonChunks;
import com.robertx22.age_of_exile.dimension.rules.OnTickSetGameMode;
import com.robertx22.age_of_exile.mixin_methods.OnItemStoppedUsingCastImbuedSpell;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.*;
import com.robertx22.age_of_exile.vanilla_mc.packets.SyncAreaLevelPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellClientEntityIsCastingSpellPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class OnServerTick {

    static List<PlayerTickAction> TICK_ACTIONS = new ArrayList<>();

    static {

        TICK_ACTIONS.add(new PlayerTickAction("spawn_bow_cast_particles", 1, (player, data) -> {
            if (OnItemStoppedUsingCastImbuedSpell.canCastImbuedSpell(player)) {
                if (Load.spells(player)
                    .getCastingData().imbued_spell_stacks > 0) {
                    ParticleUtils.spawnParticles(ParticleTypes.WITCH, player.level, player.blockPosition(), 2);
                }
            }
        }));

        TICK_ACTIONS.add(new PlayerTickAction("update_caps", 20, (player, data) -> {
            OnTickSetGameMode.onTick(player);
            CapSyncUtil.syncPerSecond(player);
            Packets.sendToClient(player, new SyncAreaLevelPacket(LevelUtils.determineLevel(player.level, player.blockPosition(), player).level));
        }));

        TICK_ACTIONS.add(new
            PlayerTickAction("second_pass", 20, (player, data) ->
        {

            if (Load.Unit(player)
                .getResources()
                .getEnergy() < Load.Unit(player)
                .getUnit()
                .energyData()
                .getValue() / 10) {
                player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 20 * 3, 1));
            }

            UnequipGear.onTick(player);

        }));

        TICK_ACTIONS.add(new

            PlayerTickAction("regen", 60, (player, data) ->

        {

            if (player.isAlive()) {

                PopulateDungeonChunks.tryPopulateChunksAroundPlayer(player.level, player);

                EntityData unitdata = Load.Unit(player);

                unitdata.tryRecalculateStats();

                RestoreResourceEvent mana = EventBuilder.ofRestore(player, player, ResourceType.mana, RestoreType.regen, 0)
                    .build();
                mana.Activate();

                if (!player.isSprinting()) {
                    RestoreResourceEvent energy = EventBuilder.ofRestore(player, player, ResourceType.energy, RestoreType.regen, 0)
                        .build();
                    energy.Activate();
                }

                boolean restored = false;

                boolean canHeal = player.getFoodData()
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

                        float exhaustion = (float) ServerContainer.get().REGEN_HUNGER_COST.get()
                            .floatValue() * percentHealed;

                        player.getFoodData()
                            .addExhaustion(exhaustion);

                    }
                }

            }
        }));

        TICK_ACTIONS.add(new PlayerTickAction("gear_soul_gen_in_inventory", 20, (player, data) ->
        {
            GearSoulOnInvTick.checkAndGenerate(player);
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

            Spell spell = Load.spells(player)
                .getCastingData()
                .getSpellBeingCast();

            if (spell != null) {
                spell.getAttached()
                    .tryActivate(Spell.CASTER_NAME, SpellCtx.onTick(player, player, EntitySavedSpellData.create(Load.Unit(player)
                        .getLevel(), player, spell)));

                PlayerUtils.getNearbyPlayers(player, 50)
                    .forEach(x -> {
                        Packets.sendToClient(x, new TellClientEntityIsCastingSpellPacket(player, spell));
                    });

            }
        }));

        TICK_ACTIONS.add(new

            PlayerTickAction("level_warning", 200, (player, data) ->

        {

            if (!WorldUtils.isMapWorldClass(player.level)) {

                boolean wasnt = false;
                if (!data.isInHighLvlZone) {
                    wasnt = true;
                }

                int lvl = Load.Unit(player)
                    .getLevel();

                if (lvl < 20) {
                    data.isInHighLvlZone = LevelUtils.determineLevel(player.level, player.blockPosition(), player).level - lvl > 10;

                    if (wasnt && data.isInHighLvlZone) {
                        OnScreenMessageUtils.sendMessage(
                            player,
                            new StringTextComponent("YOU ARE ENTERING").withStyle(TextFormatting.RED)
                                .withStyle(TextFormatting.BOLD),
                            new StringTextComponent("A HIGH LEVEL ZONE").withStyle(TextFormatting.RED)
                                .withStyle(TextFormatting.BOLD));
                    }
                }
            }
        }));

    }

    public static HashMap<UUID, PlayerTickData> PlayerTickDatas = new HashMap<UUID, PlayerTickData>();

    public static void onEndTick(MinecraftServer server) {

        for (ServerPlayerEntity player : server.getPlayerList()
            .getPlayers()) {

            try {

                PlayerTickData data = PlayerTickDatas.get(player.getUUID());

                if (data == null) {
                    data = new PlayerTickData();
                }

                data.tick(player);

                PlayerTickDatas.put(player.getUUID(), data);

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
