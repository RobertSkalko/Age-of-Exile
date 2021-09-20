package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class DashUtils {

    public enum Way {
        FORWARDS, BACKWARDS, UPWARDS;
    }

    public enum Strength {
        SHORT_DISTANCE(0.4F), MEDIUM_DISTANCE(0.8F), LARGE_DISTANCE(1.5F);

        Strength(float num) {
            this.num = num;
        }

        public float num;

    }

    public static void knockback(LivingEntity source, LivingEntity target) {

        target.knockback(0.5F, source.getX() - target.getX(), source.getZ() - target.getZ());

        if (target instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) target).connection.send(new SEntityVelocityPacket(target));
            target.hurtMarked = false;
        }
    }

    // copied from knockback, without knockback resist
    public static void push(Entity en, float f, double d, double e) {

        if (f > 0.0F) {
            en.hasImpulse = true;
            Vector3d vec3d = en.getDeltaMovement();
            Vector3d vec3d2 = (new Vector3d(d, 0.0D, e)).normalize()
                .scale((double) f);
            en.setDeltaMovement(vec3d.x / 2.0D - vec3d2.x, en.isOnGround() ? Math.min(0.4D, vec3d.y / 2.0D + (double) f) : vec3d.y, vec3d.z / 2.0D - vec3d2.z);
        }
    }

    public static void dash(LivingEntity entity, float str, Way way) {

        double x;
        double z;

        final float importantValue = 0.017453292f;

        if (way == Way.BACKWARDS) {
            x = (double) -MathHelper.sin(entity.yRot * importantValue);
            z = (double) (MathHelper.cos(entity.yRot * importantValue));
        }
        if (way == Way.UPWARDS) {
            entity.push(0, str, 0);
            return;
        } else {
            x = (double) MathHelper.sin(entity.yRot * importantValue);
            z = (double) -(MathHelper.cos(entity.yRot * importantValue));
        }

        push(entity, str, x, z);

        if (entity instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) entity).connection.send(new SEntityVelocityPacket(entity));
            entity.hurtMarked = false;
        }
    }

    public static void dash(LivingEntity entity, Strength str, Way way) {
        dash(entity, str.num, way);
    }

}
