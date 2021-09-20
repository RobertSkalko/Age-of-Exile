package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LookUtils {

    public static Entity getEntityLookedAt(Entity e) {
        List<Entity> list = getEntityLookedAt(e, 32, true);
        return list.isEmpty() ? null : list.get(0);
    }

    public static List<LivingEntity> getLivingEntityLookedAt(Entity e, double distance, boolean onlyfirst) {
        return getEntityLookedAt(e, distance, onlyfirst).stream()
            .filter(x -> x instanceof LivingEntity)
            .map(x -> (LivingEntity) x)
            .collect(Collectors.toList());
    }

    public static List<Entity> getEntityLookedAt(Entity e, double distance, boolean onlyfirst) {
        Entity foundEntity = null;

        RayTraceResult pos = raycast(e, distance);

        List<Entity> list = new ArrayList<>();

        Vector3d positionVector = e.position();
        if (e instanceof PlayerEntity)
            positionVector = positionVector.add(0, e.getEyeHeight(), 0);

        if (pos != null)
            distance = pos.getLocation()
                .distanceTo(positionVector);

        Vector3d lookVector = e.getLookAngle();
        Vector3d reachVector = positionVector.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);

        Entity lookedEntity = null;
        List<Entity> entitiesInBoundingBox = e.getCommandSenderWorld()
            .getEntities(e, e.getBoundingBox()
                .inflate(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance)
                .expandTowards(1.2F, 1.2F, 1.2F));

        double minDistance = distance;

        for (Entity entity : entitiesInBoundingBox) {
            if (entity.isPickable()) {
                float collisionBorderSize = entity.getPickRadius();
                AxisAlignedBB hitbox = entity.getBoundingBox()
                    .expandTowards(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                Optional<Vector3d> interceptPosition = hitbox.clip(positionVector, reachVector);
                Vector3d interceptVec = interceptPosition.orElse(null);

                if (hitbox.contains(positionVector)) {
                    if (0.0D < minDistance || minDistance == 0.0D) {
                        lookedEntity = entity;
                        minDistance = 0.0D;
                    }
                } else if (interceptVec != null) {
                    double distanceToEntity = positionVector.distanceTo(interceptVec);

                    if (distanceToEntity < minDistance || minDistance == 0.0D) {
                        lookedEntity = entity;
                        minDistance = distanceToEntity;
                    }
                }
            }

            if (lookedEntity != null && (minDistance < distance || pos == null))
                foundEntity = lookedEntity;
        }

        if (onlyfirst) {
            if (foundEntity != null) {
                list.add(foundEntity);
            }
        } else {
            list.addAll(entitiesInBoundingBox);
        }

        return list;
    }

    public static RayTraceResult raycast(Entity e, double len) {
        Vector3d vec = new Vector3d(e.getX(), e.getY(), e.getZ());
        if (e instanceof PlayerEntity)
            vec = vec.add(new Vector3d(0, e.getEyeHeight(), 0));

        Vector3d look = e.getLookAngle();
        if (look == null)
            return null;

        return raycast(e.getCommandSenderWorld(), vec, look, e, len);
    }

    public static RayTraceResult raycast(World world, Vector3d origin, Vector3d ray, Entity e,
                                         double len) {
        Vector3d end = origin.add(ray.normalize()
            .scale(len));
        RayTraceResult pos = world.clip(new RayTraceContext(origin, end, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, e));
        return pos;
    }

}




