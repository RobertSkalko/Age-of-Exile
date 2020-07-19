package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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

        if (world.isClient) {
            return ClientOnly.getEntityByUUID(world, id);
        } else {
            return ServerOnly.getEntityByUUID(world, id);
        }

    }

    public static Vec3d getEndOfLook(LivingEntity entity, double distance) {
        return entity.getCameraPosVec(0.5F)
            .add(entity.getRotationVector()
                .multiply(distance));
    }

    public static void spawnParticlesForTesting(Box aabb, World world) {

        if (!world.isClient) {
            for (double x = aabb.minX; x < aabb.maxX; x += 0.3F) {
                for (double y = aabb.minY; y < aabb.maxY; y += 1F) {
                    for (double z = aabb.minZ; z < aabb.maxZ; z += 0.3F) {

                        for (int i = 0; i < 1; i++) {
                            ((ServerWorld) world).spawnParticles(
                                ParticleTypes.HAPPY_VILLAGER, x, y, z, 0, 0.0D, 0.0D, 0.0D, 0F);
                        }
                    }
                }
            }
        }

    }

    public static double getPlayerEyesPos(LivingEntity player) {
        return player.getBoundingBox().minY + player.getStandingEyeHeight();
    }

}
