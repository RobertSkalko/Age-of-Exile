package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class LookUtils {
    public static Entity getEntityLookedAt(Entity e) {
        Entity foundEntity = null;

        final double finalDistance = 32;
        double distance = finalDistance;
        HitResult pos = raycast(e, finalDistance);

        Vec3d positionVector = e.getPos();
        if (e instanceof PlayerEntity)
            positionVector = positionVector.add(0, e.getStandingEyeHeight(), 0);

        if (pos != null)
            distance = pos.getPos()
                .distanceTo(positionVector);

        Vec3d lookVector = e.getRotationVector();
        Vec3d reachVector = positionVector.add(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance);

        Entity lookedEntity = null;
        List<Entity> entitiesInBoundingBox = e.getEntityWorld()
            .getEntities(e, e.getBoundingBox()
                .expand(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance)
                .stretch(1F, 1F, 1F));
        double minDistance = distance;

        for (Entity entity : entitiesInBoundingBox) {
            if (entity.collides()) {
                float collisionBorderSize = entity.getTargetingMargin();
                Box hitbox = entity.getBoundingBox()
                    .stretch(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                Optional<Vec3d> interceptPosition = hitbox.rayTrace(positionVector, reachVector);
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

        return foundEntity;
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
        HitResult pos = world.rayTrace(new RayTraceContext(origin, end, RayTraceContext.ShapeType.OUTLINE, RayTraceContext.FluidHandling.NONE, e));
        return pos;
    }

}




