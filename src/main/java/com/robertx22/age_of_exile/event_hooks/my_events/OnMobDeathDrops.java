package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusXpToMobsOfTier;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.dimension.dungeon_data.SingleDungeonData;
import com.robertx22.age_of_exile.loot.LootUtils;
import com.robertx22.age_of_exile.loot.MasterLootGen;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.components.EntityInfoComponent;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class OnMobDeathDrops extends EventConsumer<ExileEvents.OnMobDeath> {

    @Override
    public void accept(ExileEvents.OnMobDeath onMobDeath) {
        LivingEntity mobKilled = onMobDeath.mob;

        try {

            if (mobKilled.level.isClientSide) {
                return;
            }

            if (!(mobKilled instanceof PlayerEntity)) {

                EntityData mobKilledData = Load.Unit(mobKilled);

                LivingEntity killerEntity = EntityInfoComponent.get(mobKilled)
                    .getDamageStats()
                    .getHighestDamager((ServerWorld) mobKilled.level);

                if (killerEntity == null) {
                    try {
                        if (mobKilled.getLastDamageSource()
                            .getEntity() instanceof PlayerEntity) {
                            killerEntity = (LivingEntity) mobKilled.getLastDamageSource()
                                .getEntity();
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
                    EntityData playerData = Load.Unit(player);

                    EntityConfig config = ExileDB.getEntityConfig(mobKilled, mobKilledData);

                    float loot_multi = (float) config.loot_multi;
                    float exp_multi = (float) config.exp_multi;

                    if (loot_multi > 0) {
                        MasterLootGen.genAndDrop(mobKilled, player);

                    }
                    if (exp_multi > 0) {
                        GiveExp(mobKilled, player, playerData, mobKilledData, exp_multi);
                    }

                    if (WorldUtils.isDungeonWorld(mobKilled.level)) {
                        if (EntityTypeUtils.isMob(mobKilled)) {
                            SingleDungeonData dungeon = Load.dungeonData(mobKilled.level).data.get(mobKilled.blockPosition());
                            dungeon.quest.increaseProgressBy(player, 1, dungeon);
                        }
                    }

                }

            }

        } catch (

            Exception e) {
            e.printStackTrace();
        }

    }

    private static void GiveExp(LivingEntity victim, PlayerEntity killer, EntityData killerData, EntityData mobData, float multi) {

        float exp = LevelUtils.getBaseExpMobReward(mobData.getLevel());

        if (exp < 1) {
            exp++;
        }

        exp *= LootUtils.getLevelDistancePunishmentMulti(mobData.getLevel(), killerData.getLevel());

        exp *= ExileDB.MobRarities()
            .get(mobData.getRarity())
            .expMulti();

        if (WorldUtils.isMapWorldClass(victim.level)) {
            SingleDungeonData data = Load.dungeonData(victim.level).data.get(victim.blockPosition());
            if (!data.data.isEmpty()) {
                exp *= data.data.team.lootMulti;
            }
        }

        float baseexp = exp;

        exp += (-1F + multi) * baseexp;

        exp += (-1F + ServerContainer.get().EXP_GAIN_MULTI.get()) * baseexp;

        exp += (-1F + Load.playerRPGData(killer).favor
            .getRank().exp_multi) * baseexp;

        exp += (-1F + ExileDB.getDimensionConfig(victim.level).exp_multi) * baseexp;

        exp += (-1F + LootUtils.getMobHealthBasedLootMulti(mobData, killer)) * baseexp;

        exp += (-1F + killerData.getUnit()
            .getCalculatedStat(new BonusXpToMobsOfTier(LevelUtils.levelToSkillTier(mobData.getLevel())))
            .getMultiplier()) * baseexp;

        exp += (-1F + killerData.getUnit()
            .getCalculatedStat(BonusExp.getInstance())
            .getMultiplier()) * baseexp;

        exp = ExileEvents.MOB_EXP_DROP.callEvents(new ExileEvents.OnMobExpDrop(victim, exp)).exp;

        if ((int) exp > 0) {

            List<PlayerEntity> list = TeamUtils.getOnlineTeamMembersInRange(killer);

            int members = list.size() - 1;
            if (members > 5) {
                members = 5;
            }

            float teamMulti = 1 + (0.1F * members);

            exp *= teamMulti;

            exp /= list.size();

            if (exp > 0) {

                for (PlayerEntity x : list) {
                    Load.Unit(x)
                        .GiveExp(x, (int) exp);
                }
            }

        }
    }

}
