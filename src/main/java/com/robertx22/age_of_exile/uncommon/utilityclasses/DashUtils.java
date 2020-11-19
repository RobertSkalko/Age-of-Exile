package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;

public class DashUtils {

    public enum Way {
        FORWARDS, BACKWARDS;
    }

    public enum Strength {
        SHORT_DISTANCE(0.4F), MEDIUM_DISTANCE(0.8F), LARGE_DISTANCE(1.5F);

        Strength(float num) {
            this.num = num;
        }

        public float num;

    }

    public static void knockback(LivingEntity source, LivingEntity target) {

        target.takeKnockback(0.5F, source.getX() - target.getX(), source.getZ() - target.getZ());

        if (target instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) target).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(target));
            target.velocityModified = false;
        }
    }

    public static void dash(LivingEntity entity, float str, Way way) {

        double x;
        double z;

        final float importantValue = 0.017453292f;

        if (way == Way.BACKWARDS) {
            x = (double) -MathHelper.sin(entity.yaw * importantValue);
            z = (double) (MathHelper.cos(entity.yaw * importantValue));
        } else {
            x = (double) MathHelper.sin(entity.yaw * importantValue);
            z = (double) -(MathHelper.cos(entity.yaw * importantValue));
        }

        // this is removed with knockback resistance, TODO
        entity.takeKnockback(str, x, z);

        if (entity instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) entity).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(entity));
            entity.velocityModified = false;
        }
    }

    public static void dash(LivingEntity entity, Strength str, Way way) {
        dash(entity, str.num, way);
    }

}
