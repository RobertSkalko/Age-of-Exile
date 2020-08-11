package com.robertx22.mine_and_slash.event_hooks.ontick;

import com.robertx22.mine_and_slash.capability.bases.CapSyncUtil;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.HealthRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShieldRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.ManaRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourcesData;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.UUID;

public class OnServerTick implements ServerTickEvents.EndTick {

    static final int TicksToUpdatePlayer = 18;
    static final int TicksToRegen = 60; // was 100, todo balance
    static final int TicksToPassMinute = 1200;
    static final int TicksToSpellCooldowns = 1;
    static final int TicksToCompatibleItems = 20;

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

                        float manarestored = unit.peekAtStat(ManaRegen.GUID)
                            .getAverageValue();
                        manarestored += unit.peekAtStat(RegeneratePercentStat.MANA)
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

                            float healthrestored = unit.peekAtStat(HealthRegen.GUID)
                                .getAverageValue();
                            healthrestored += unit.peekAtStat(RegeneratePercentStat.HEALTH)
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

                            float magicshieldrestored = unit.peekAtStat(MagicShieldRegen.GUID)
                                .getAverageValue();
                            magicshieldrestored += unit.peekAtStat(RegeneratePercentStat.MAGIC_SHIELD)
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
                    CompatibleItemInventoryCheck.checkAndGenerate(player);
                    data.ticksToCompItems = 0;

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

                }

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

        public void increment() {
            regenTicks++;
            playerSyncTick++;
            ticksToPassMinute++;
            ticksToCompItems++;
            ticksToSpellCooldowns++;
        }

    }

}
