package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.capability.bases.CapSyncUtil;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.CompatibleItemUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
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

                data.increment();

                if (data.regenTicks > TicksToRegen) {
                    data.regenTicks = 0;
                    if (player.isAlive()) {

                        EntityCap.UnitData unitdata = Load.Unit(player);

                        unitdata.tryRecalculateStats(player);
                        // has to do
                        // this cus curios doesnt call
                        // equipsChanged event - actually
                        // there's one, but i fear  bugs

                        Unit unit = unitdata.getUnit();

                        float manarestored = unit.getCalculatedStat(ManaRegen.GUID)
                            .getAverageValue();
                        manarestored += unit.getCalculatedStat(RegeneratePercentStat.MANA)
                            .getAverageValue() * unit.manaData()
                            .getAverageValue() / 100F;

                        ResourcesData.Context mana = new ResourcesData.Context(unitdata, player, ResourcesData.Type.MANA,
                            manarestored,
                            ResourcesData.Use.RESTORE
                        );
                        unitdata.getResources()
                            .modify(mana);

                        boolean restored = false;

                        boolean canHeal = player.getHungerManager()
                            .getFoodLevel() >= 16;

                        if (canHeal) {
                            if (player.getHealth() < player.getMaxHealth()) {
                                restored = true;
                            }

                            float healthrestored = unit.getCalculatedStat(HealthRegen.GUID)
                                .getAverageValue();
                            healthrestored += unit.getCalculatedStat(RegeneratePercentStat.HEALTH)
                                .getAverageValue() * player.getMaxHealth() / 100F;
                            ResourcesData.Context hp = new ResourcesData.Context(unitdata, player, ResourcesData.Type.HEALTH,
                                healthrestored,
                                ResourcesData.Use.RESTORE
                            );

                            unitdata.getResources()
                                .modify(hp);

                            if (unitdata.getResources()
                                .getMagicShield() < unitdata.getUnit()
                                .magicShieldData()
                                .getAverageValue()) {
                                restored = true;
                            }

                            float magicshieldrestored = unit.getCalculatedStat(MagicShieldRegen.GUID)
                                .getAverageValue();
                            magicshieldrestored += unit.getCalculatedStat(RegeneratePercentStat.MAGIC_SHIELD)
                                .getAverageValue() * unit.magicShieldData()
                                .getAverageValue() / 100F;
                            ResourcesData.Context ms = new ResourcesData.Context(unitdata, player,
                                ResourcesData.Type.MAGIC_SHIELD,
                                magicshieldrestored,
                                ResourcesData.Use.RESTORE
                            );
                            unitdata.getResources()
                                .modify(ms);

                            if (restored) {
                                unitdata.syncToClient(player);

                                float percentHealed = healthrestored / player.getMaxHealth();

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

                    boolean wasnt = false;
                    if (!data.isInHighLvlZone) {
                        wasnt = true;
                    }

                    int lvl = Load.Unit(player)
                        .getLevel();

                    if (lvl < 20) {
                        data.isInHighLvlZone = Math.abs(lvl - LevelUtils.determineLevel(player.world, player.getBlockPos(), player)) > 10;

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

                    CapSyncUtil.syncAll(player);
                    Packets.sendToClient(player, new SyncAreaLevelPacket(LevelUtils.determineLevel(player.world, player.getBlockPos(), player)));
                }

                UnequipHighLevelGear.onTick(player);

                PlayerTickDatas.put(player.getUuid(), data);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    static class PlayerTickData {
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
