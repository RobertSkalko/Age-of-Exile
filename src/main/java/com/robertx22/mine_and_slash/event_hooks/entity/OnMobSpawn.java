package com.robertx22.mine_and_slash.event_hooks.entity;

import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.rarities.MobRarity;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.PlayerUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class OnMobSpawn implements ServerEntityEvents.Load {

    @Override
    public void onLoad(Entity entity, ServerWorld serverWorld) {

        if (!(entity instanceof LivingEntity)) {
            return;
        }
        if (entity instanceof PlayerEntity) {
            return;
        }

        setupNewMobOnSpawn((LivingEntity) entity);

    }

    public static void setupNewMobOnSpawn(LivingEntity entity) {

        if (entity.world.isClient) {
            throw new RuntimeException("Don't run this code on client!");
        }

        UnitData endata = Load.Unit(entity);

        if (endata != null) {

            if (endata.getUnit() != null) {
                endata.getUnit()
                    .removeUnregisteredStats();
            }

            endata.setType(entity);

            PlayerEntity nearestPlayer = null;

            nearestPlayer = PlayerUtils.nearestPlayer((ServerWorld) entity.world, entity);

            if (endata.needsToBeGivenStats()) {
                Unit unit = Mob(entity, endata, nearestPlayer);
                endata.mobStatsAreSet();
                entity.heal(entity.getMaximumHealth());
            } else {
                if (endata.getUnit() == null) {
                    endata.setUnit(new Unit(), entity);
                }

                endata.getUnit()
                    .initStats(); // give new stats to mob on spawn
                endata.forceRecalculateStats(entity);
            }

        }

    }

    public static Unit Mob(LivingEntity entity, UnitData data,
                           PlayerEntity nearestPlayer) {

        Unit mob = new Unit();
        mob.initStats();

        UnitData endata = Load.Unit(entity);

        endata.SetMobLevelAtSpawn(entity, nearestPlayer);

        int rar = mob.randomRarity(entity, endata);
        endata.setRarity(rar);

        MobRarity rarity = Rarities.Mobs.get(rar);
        mob.setRandomMobAffixes(rarity);

        endata.setUnit(mob, entity);

        mob.recalculateStats(entity, endata, null);

        return mob;

    }

}
