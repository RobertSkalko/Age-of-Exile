package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public final class Utilities {

    public static LivingEntity getLivingEntityByUUID(World world, UUID id) {

        Entity en = getEntityByUUID(world, id);

        if (en instanceof LivingEntity) {
            return (LivingEntity) en;
        } else {
            return null;
        }

    }

    public static Entity getEntityByUUID(World world, UUID id) {

        if (id == null)
            return null;

        if (world.isClientSide) {
            return ClientOnly.getEntityByUUID(world, id);
        } else {
            return ServerOnly.getEntityByUUID(world, id);
        }

    }

    public static Vector3d getEndOfLook(LivingEntity entity, double distance) {
        return entity.getEyePosition(0.5F)
            .add(entity.getLookAngle()
                .scale(distance));
    }

    public static void spawnParticlesForTesting(AABB aabb, World world) {
        if (!world.isClientSide) {
            for (double x = aabb.minX; x < aabb.maxX; x += 0.3F) {
                for (double y = aabb.minY; y < aabb.maxY; y += 1F) {
                    for (double z = aabb.minZ; z < aabb.maxZ; z += 0.3F) {

                        for (int i = 0; i < 1; i++) {
                            ((ServerWorld) world).sendParticles(
                                ParticleTypes.HAPPY_VILLAGER, x, y, z, 0, 0.0D, 0.0D, 0.0D, 0F);
                        }
                    }
                }
            }
        }
    }

}
