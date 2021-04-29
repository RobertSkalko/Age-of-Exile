package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.capability.bases.CapSyncUtil;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.dimension.rules.OnTickGiveTpBack;
import com.robertx22.age_of_exile.dimension.rules.OnTickSetGameMode;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.RegenEvent;
import com.robertx22.age_of_exile.uncommon.utilityclasses.CompatibleItemUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.ForceChoosingRace;
import com.robertx22.age_of_exile.vanilla_mc.packets.SyncAreaLevelPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.UUID;

public class OnServerTick implements ServerTickEvents.EndTick {

    static final int TicksToUpdatePlayer = 18;
    static final int TicksToRegen = 60; // was 100, todo balance
    static final int TicksToPassMinute = 1200;
    static final int TicksToSpellCooldowns = 1;
    static final int TicksToCompatibleItems = 40;
    static final int TicksToLevelWarning = 200;

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

                Spell spell = Load.spells(player)
                    .getCastingData()
                    .getSpellBeingCast();

                if (spell != null) {
                    SpellCastContext ctx = new SpellCastContext(player, 0, spell);
                    spell
                        .getAttached()
                        .tryActivate(Spell.CASTER_NAME, SpellCtx.onTick(player, player, EntitySavedSpellData.create(ctx.skillGemData.lvl, player, spell, ctx.spellConfig)));
                }

                data.increment();

                if (data.regenTicks > TicksToRegen) {

                    data.regenTicks = 0;
                    if (player.isAlive()) {

                        EntityCap.UnitData unitdata = Load.Unit(player);

                        unitdata.getCooldowns()
                            .onTicksPass(TicksToRegen);

                        unitdata.getResources()
                            .shields.onTicksPassed(TicksToRegen);

                        unitdata.tryRecalculateStats();

                        RegenEvent event = new RegenEvent(player, player, ResourceType.MANA);
                        event.Activate();

                        boolean restored = false;

                        boolean canHeal = player.getHungerManager()
                            .getFoodLevel() >= 16;

                        if (canHeal) {
                            if (player.getHealth() < player.getMaxHealth()) {
                                restored = true;
                            }

                            RegenEvent hpevent = new RegenEvent(player, player, ResourceType.HEALTH);
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

                    OnTickGiveTpBack.give(player);

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

                if (data.ticksToSpellCooldowns >= TicksToSpellCooldowns) {
                    data.ticksToSpellCooldowns = 0;

                    Load.spells(player)
                        .getCastingData()
                        .onTimePass(player, Load.spells(player), TicksToSpellCooldowns);
                }

                if (data.playerSyncTick > TicksToUpdatePlayer) {
                    data.playerSyncTick = 0;

                    OnTickSetGameMode.onTick(player);

                    if (!Load.Unit(player)
                        .hasRace()) {
                        Packets.sendToClient(player, new ForceChoosingRace());
                    }

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

        public boolean isInHighLvlZone = false;

        public void increment() {
            regenTicks++;
            playerSyncTick++;
            ticksToPassMinute++;
            ticksToCompItems++;
            ticksToSpellCooldowns++;
            ticksToLvlWarning++;

        }

    }

}
