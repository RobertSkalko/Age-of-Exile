package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
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

        HitResult pos = raycast(e, distance);

        List<Entity> list = new ArrayList<>();

        Vec3d positionVector = e.getPos();
        if (e instanceof PlayerEntity)
            positionVector = positionVector.add(0, e.getStandingEyeHeight(), 0);

        if (pos != null)
            distance = pos.getPos()
                .distanceTo(positionVector);

        Vec3d lookVector = e.getRotationVector();
        Vec3d reachVector = positionVector.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);

        Entity lookedEntity = null;
        List<Entity> entitiesInBoundingBox = e.getEntityWorld()
            .getOtherEntities(e, e.getBoundingBox()
                .expand(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance)
                .stretch(1.2F, 1.2F, 1.2F));

        double minDistance = distance;

        for (Entity entity : entitiesInBoundingBox) {
            if (entity.collides()) {
                float collisionBorderSize = entity.getTargetingMargin();
                Box hitbox = entity.getBoundingBox()
                    .stretch(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                Optional<Vec3d> interceptPosition = hitbox.raycast(positionVector, reachVector);
                Vec3d interceptVec = interceptPosition.orElse(null);

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

    public static HitResult raycast(Entity e, double len) {
        Vec3d vec = new Vec3d(e.getX(), e.getY(), e.getZ());
        if (e instanceof PlayerEntity)
            vec = vec.add(new Vec3d(0, e.getStandingEyeHeight(), 0));

        Vec3d look = e.getRotationVector();
        if (look == null)
            return null;

        return raycast(e.getEntityWorld(), vec, look, e, len);
    }

    public static HitResult raycast(World world, Vec3d origin, Vec3d ray, Entity e,
                                    double len) {
        Vec3d end = origin.add(ray.normalize()
            .multiply(len));
        HitResult pos = world.raycast(new RaycastContext(origin, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, e));
        return pos;
    }

}




