package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.loot.LootUtils;
import com.robertx22.age_of_exile.loot.MasterLootGen;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.library_of_exile.components.EntityInfoComponent;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

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

                    LivingEntity killerEntity = EntityInfoComponent.get(mobKilled)
                        .getDamageStats()
                        .getHighestDamager((ServerWorld) mobKilled.world);

                    if (killerEntity == null) {
                        try {
                            if (mobKilled.getRecentDamageSource()
                                .getAttacker() instanceof PlayerEntity) {
                                killerEntity = (LivingEntity) mobKilled.getRecentDamageSource()
                                    .getAttacker();
                            }
                        } catch (Exception e) {
                        }
                    }

                    if (killerEntity == null) {
                        if (EntityInfoComponent.get(mobKilled)
                            .getDamageStats()
                            .getEnviroOrMobDmg() < mobKilled.getMaxHealth() / 2F) {
                            killerEntity = onMobDeath.killer;
                        }
                    }

                    if (killerEntity instanceof ServerPlayerEntity) {

                        ServerPlayerEntity player = (ServerPlayerEntity) killerEntity;
                        UnitData playerData = Load.Unit(player);

                        EntityConfig config = SlashRegistry.getEntityConfig(mobKilled, mobKilledData);

                        float loot_multi = (float) config.loot_multi;
                        float exp_multi = (float) config.exp_multi;

                        if (loot_multi > 0) {
                            MasterLootGen.genAndDrop(mobKilled, player);
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

        float exp = LevelUtils.getBaseExpMobReward(mobData.getLevel());

        if (exp < 1) {
            exp++;
        }

        exp *= SlashRegistry.getEntityConfig(victim, mobData).exp_multi;

        exp *= multi;

        exp *= SlashRegistry.MobRarities()
            .get(mobData.getRarity())
            .expMulti();

        exp *= LootUtils.getLevelDistancePunishmentMulti(mobData.getLevel(), killerData.getLevel());

        exp *= LootUtils.getMobHealthBasedLootMulti(mobData, killer);

        exp *= killerData.getUnit()
            .getCalculatedStat(BonusExp.getInstance())
            .getMultiplier();

        exp = ExileEvents.MOB_EXP_DROP.callEvents(new ExileEvents.OnMobExpDrop(victim, exp)).exp;

        exp *= Load.favor(killer)
            .getRank().exp_multi;

        if ((int) exp > 0) {

            List<PlayerEntity> list = TeamUtils.getOnlineTeamMembers(killer);

            exp /= list.size();

            if (exp > 0) {

                MutableText txt = new LiteralText("+" + (int) exp + " Experience").formatted(Formatting.GREEN);

                for (PlayerEntity x : list) {
                    OnScreenMessageUtils.sendMessage((ServerPlayerEntity) x, txt, TitleS2CPacket.Action.ACTIONBAR);
                    Load.Unit(x)
                        .GiveExp(x, (int) exp);
                }
            }

        }
    }

}
