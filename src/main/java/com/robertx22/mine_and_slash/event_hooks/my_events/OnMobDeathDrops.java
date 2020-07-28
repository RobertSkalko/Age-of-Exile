package com.robertx22.mine_and_slash.event_hooks.my_events;

import com.robertx22.exiled_lib.events.base.EventConsumer;
import com.robertx22.exiled_lib.events.base.ExileEvents;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.EntityConfig;
import com.robertx22.mine_and_slash.loot.LootUtils;
import com.robertx22.mine_and_slash.loot.MasterLootGen;
import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.LevelUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.NumberUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TeamUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.DmgNumPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class OnMobDeathDrops extends EventConsumer<ExileEvents.OnMobDeath> {

    @Override
    public void accept(ExileEvents.OnMobDeath onMobDeath) {
        LivingEntity mobKilled = onMobDeath.mob;

        try {

            if (mobKilled.world.isClient) {
                return;
            }

            if (!(mobKilled instanceof PlayerEntity)) {
                if (Load.hasUnit(mobKilled)) {

                    UnitData mobKilledData = Load.Unit(mobKilled);

                    LivingEntity killerEntity = mobKilledData.getHighestDamageEntity(mobKilled);

                    if (killerEntity instanceof ServerPlayerEntity) {

                        ServerPlayerEntity player = (ServerPlayerEntity) killerEntity;
                        UnitData playerData = Load.Unit(player);

                        EntityConfig config = SlashRegistry.getEntityConfig(mobKilled, mobKilledData);

                        float loot_multi = (float) config.loot_multi;
                        float exp_multi = (float) config.exp_multi;

                        if (loot_multi > 0) {
                            Load.antiMobFarm(player.world)
                                .onValidMobDeathByPlayer(mobKilled);
                        }

                        if (loot_multi > 0) {
                            MasterLootGen.genAndDrop(mobKilledData, playerData, mobKilled, player);
                        }
                        if (exp_multi > 0) {
                            GiveExp(mobKilled, player, playerData, mobKilledData, exp_multi);
                        }

                    }
                }

            }

        } catch (

            Exception e) {
            e.printStackTrace();
        }

    }

    private static void GiveExp(LivingEntity victim, PlayerEntity killer, UnitData killerData, UnitData mobData, float multi) {

        float exp = LevelUtils.getExpDropForLevel(victim, mobData.getLevel());

        if (exp < 1) {
            exp++;
        }

        exp *= SlashRegistry.getEntityConfig(victim, mobData).exp_multi;

        exp *= Load.antiMobFarm(victim.world)
            .getDropMultiForMob(victim);

        exp *= multi;

        exp *= Rarities.Mobs.get(mobData.getRarity())
            .expMulti();

        exp = (int) LootUtils.ApplyLevelDistancePunishment(mobData, killerData, exp);

        exp = ExileEvents.MOB_EXP_DROP.callEvents(new ExileEvents.OnMobExpDrop(victim, exp)).exp;

        try {
            exp *= Load.antiMobFarm(victim.world)
                .getDropMultiForMob(victim);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (victim instanceof SlimeEntity) {
            exp /= 10;
        }

        if (exp > 0) {

            List<PlayerEntity> list = TeamUtils.getOnlineTeamMembers(killer);

            exp /= list.size();

            if (exp > 0) {
                DmgNumPacket packet = new DmgNumPacket(
                    victim, Elements.Nature, "+" + NumberUtils.format(exp) + " Exp!", exp);
                packet.isExp = true;
                Packets.sendToClient(killer, packet);

                for (PlayerEntity x : list) {
                    Load.Unit(x)
                        .GiveExp(x, (int) exp);
                }
            }

        }
    }

}
