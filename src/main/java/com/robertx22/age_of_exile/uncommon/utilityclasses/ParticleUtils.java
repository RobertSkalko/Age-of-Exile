package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleUtils {
    public static void spawnDefaultSlashingWeaponParticles(Entity en) {
        SoundUtils.playSound(en, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);

        if (en instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) en;
            p.spawnSweepAttackParticles();
        }
    }

    public static void spawn(ParticleEffect type, World world, Vec3d vec, Vec3d mot) {
        world.addParticle(type, vec.x, vec.y, vec.z, mot.x, mot.y, mot.z);
    }

    public static void spawn(ParticleEffect type, World world, Vec3d vec) {
        world.addParticle(type, vec.x, vec.y, vec.z, 0, 0, 0);
    }

    public static void spawn(ParticleEffect particleData, World world, double x, double y, double z, double xSpeed,
                             double ySpeed, double zSpeed) {
        world.addParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);

    }

    public static void spawnParticles(ParticleType particle, World world, BlockPos pos, int amount) {

        ParticleEnum.sendToClients(pos, world, new ParticlePacketData(pos, ParticleEnum.AOE).radius(1)
            .type(particle)
            .amount(amount));

    }

}
