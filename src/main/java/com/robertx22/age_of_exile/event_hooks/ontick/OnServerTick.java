package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.areas.AreaData;
import com.robertx22.age_of_exile.capability.bases.CapSyncUtil;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.world.WorldAreas;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.CompatibleItemUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OnServerTick implements ServerTickEvents.EndTick {

    static final int TicksToUpdatePlayer = 18;
    static final int TicksToRegen = 60; // was 100, todo balance
    static final int TicksToPassMinute = 1200;
    static final int TicksToSpellCooldowns = 1;
    static final int TicksToCompatibleItems = 40;

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
                    CompatibleItemUtils.checkAndGenerate(player);
                    data.ticksToCompItems = 0;

                    // area changing notification
                    AreaData area = WorldAreas.getArea(player.world, player.getBlockPos());

                    if (data.currentArea == null || !area.uuid.equals(data.currentArea.uuid)) {
                        data.currentArea = area;

                        if (data.areasVisitedUUIDS.add(data.currentArea.uuid)) {

                            MinMax lvlrange = data.currentArea.getLevelRange(player.world, player);

                            String range = lvlrange.min + "-" + lvlrange.max;

                            if (lvlrange.max == lvlrange.min) {
                                range = lvlrange.max + "";
                            }
                            OnScreenMessageUtils.sendMessage(player, new LiteralText(data.currentArea.getName()), new LiteralText("Lvl: " + range));
                        }
                    }
                    // area changing notification
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

        AreaData currentArea = null;
        Set<String> areasVisitedUUIDS = new HashSet<>();

        public void increment() {
            regenTicks++;
            playerSyncTick++;
            ticksToPassMinute++;
            ticksToCompItems++;
            ticksToSpellCooldowns++;
        }

    }

}
