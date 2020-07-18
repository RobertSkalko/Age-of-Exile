package com.robertx22.mine_and_slash.event_hooks.ontick;

import com.robertx22.mine_and_slash.capability.bases.CapSyncUtil;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.HealthRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.MagicShieldRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.ManaRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.mine_and_slash.event_hooks.ontick.OnServerTick.PlayerTickData;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourcesData;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

import java.util.HashMap;
import java.util.UUID;

public class OnServerTick {

    static final int TicksToUpdatePlayer = 18;
    static final int TicksToRegen = 20; // was 100, todo balance
    static final int TicksToPassMinute = 1200;
    static final int TicksToSpellCooldowns = 1;
    static final int TicksToProcessChunks = 50;

    public static HashMap<UUID, PlayerTickData> PlayerTickDatas = new HashMap<UUID, PlayerTickData>();

    @SubscribeEvent
    public static void onTickLogicVoid(TickEvent.PlayerTickEvent event) {

        if (event.side.equals(LogicalSide.SERVER) && event.phase == TickEvent.Phase.END) {

            try {

                ServerPlayerEntity player = (ServerPlayerEntity) event.player;

                PlayerTickData data = PlayerTickDatas.get(player.getUuid());

                if (data == null) {
                    data = new PlayerTickData();
                }

                data.increment();

                if (data.regenTicks > TicksToRegen) {
                    data.regenTicks = 0;
                    if (player.isAlive()) {

                        player.getCapability(EntityCap.Data)
                            .ifPresent(x -> {
                                x.forceRecalculateStats(player);
                                // has to do
                                // this cus curios doesnt call
                                // equipsChanged event - actually
                                // there's one, but i fear  bugs

                                Unit unit = x.getUnit();

                                float manarestored = unit.peekAtStat(ManaRegen.GUID)
                                    .getAverageValue();
                                manarestored += unit.peekAtStat(RegeneratePercentStat.MANA)
                                    .getAverageValue() * unit.manaData()
                                    .getAverageValue() / 100F;

                                ResourcesData.Context mana = new ResourcesData.Context(x, player, ResourcesData.Type.MANA,
                                    manarestored,
                                    ResourcesData.Use.RESTORE
                                );
                                x.getResources()
                                    .modify(mana);

                                boolean restored = false;

                                boolean canHeal = player.getHungerManager()
                                    .getFoodLevel() >= 18;

                                if (canHeal) {
                                    if (player.getHealth() < player.getMaximumHealth()) {
                                        restored = true;
                                    }

                                    float healthrestored = unit.peekAtStat(HealthRegen.GUID)
                                        .getAverageValue();
                                    healthrestored += unit.peekAtStat(RegeneratePercentStat.HEALTH)
                                        .getAverageValue() * player.getMaximumHealth() / 100F;
                                    ResourcesData.Context hp = new ResourcesData.Context(x, player, ResourcesData.Type.HEALTH,
                                        healthrestored,
                                        ResourcesData.Use.RESTORE
                                    );

                                    x.getResources()
                                        .modify(hp);

                                    if (x.getResources()
                                        .getMagicShield() < x.getUnit()
                                        .magicShieldData()
                                        .getAverageValue()) {
                                        restored = true;
                                    }

                                    float magicshieldrestored = unit.peekAtStat(MagicShieldRegen.GUID)
                                        .getAverageValue();
                                    magicshieldrestored += unit.peekAtStat(RegeneratePercentStat.MAGIC_SHIELD)
                                        .getAverageValue() * unit.magicShieldData()
                                        .getAverageValue() / 100F;
                                    ResourcesData.Context ms = new ResourcesData.Context(x, player,
                                        ResourcesData.Type.MAGIC_SHIELD,
                                        magicshieldrestored,
                                        ResourcesData.Use.RESTORE
                                    );
                                    x.getResources()
                                        .modify(ms);

                                    if (restored) {

                                        float percentHealed = healthrestored / player.getMaximumHealth();

                                        float exhaustion = ModConfig.INSTANCE.Server.REGEN_HUNGER_COST.get()
                                            .floatValue() * percentHealed;

                                        player.getHungerManager()
                                            .addExhaustion(exhaustion);

                                    }
                                }

                            });
                    }
                }

                if (data.ticksToPassMinute > TicksToPassMinute) {
                    data.ticksToPassMinute = 0;

                    if (player.getServer()
                        .isSinglePlayer()) {
                        // SlashRegistry.restoreFromBackupifEmpty();
                    }
                }
                if (data.ticksToProcessChunks > TicksToProcessChunks) {
                    data.ticksToProcessChunks = 0;

                }

                if (data.ticksToSpellCooldowns >= TicksToSpellCooldowns) {
                    data.ticksToSpellCooldowns = 0;

                    player.getCapability(PlayerSpellCap.Data)
                        .ifPresent(x -> {
                            x.getCastingData()
                                .onTimePass(player, x, TicksToSpellCooldowns);
                        });
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
        public int ticksToProcessChunks = 0;

        public void increment() {
            regenTicks++;
            playerSyncTick++;
            ticksToPassMinute++;
            ticksToProcessChunks++;
            ticksToSpellCooldowns++;
        }

    }

}
