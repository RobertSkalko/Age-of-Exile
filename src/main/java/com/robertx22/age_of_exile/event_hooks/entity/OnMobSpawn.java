package com.robertx22.age_of_exile.event_hooks.entity;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.capability.world.WorldAreas;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
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
                setupNewMob(entity, endata, nearestPlayer);

            } else {
                if (endata.getUnit() == null) {
                    endata.setUnit(new Unit(), entity);
                }

                endata.getUnit()
                    .initStats(); // give new stats to mob on spawn
                endata.forceRecalculateStats(entity);
            }

            entity.heal(Integer.MAX_VALUE);

        }

    }

    public static Unit setupNewMob(LivingEntity entity, UnitData endata,
                                   PlayerEntity nearestPlayer) {

        Unit mob = new Unit();
        mob.initStats();

        endata.SetMobLevelAtSpawn(entity, nearestPlayer);

        int rar = mob.randomRarity(entity, endata);
        endata.setRarity(rar);

        MobRarity rarity = SlashRegistry.MobRarities()
            .get(rar);
        endata.getAffixData()
            .randomizeAffixes(rarity);

        endata.setUnit(mob, entity);

        endata.setAreaMod(WorldAreas.getArea(entity.world, entity.getBlockPos())
            .getAreaModifier());

        endata.mobStatsAreSet();
        endata.forceRecalculateStats(entity);

        return mob;

    }

}
