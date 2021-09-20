package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ParticleUtils {
    public static void spawnDefaultSlashingWeaponParticles(Entity en) {
        SoundUtils.playSound(en, SoundEvents.PLAYER_ATTACK_SWEEP, 1, 1);

        if (en instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) en;
            p.sweepAttack();
        }
    }

    public static void spawn(IParticleData type, World world, Vector3d vec, Vector3d mot) {
        world.addParticle(type, vec.x, vec.y, vec.z, mot.x, mot.y, mot.z);
    }

    public static void spawn(IParticleData type, World world, Vector3d vec) {
        world.addParticle(type, vec.x, vec.y, vec.z, 0, 0, 0);
    }

    public static void spawn(IParticleData particleData, World world, double x, double y, double z, double xSpeed,
                             double ySpeed, double zSpeed) {
        world.addParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);

    }

    public static void spawnParticles(ParticleType particle, World world, BlockPos pos, int amount) {

        ParticleEnum.sendToClients(pos, world, new ParticlePacketData(pos, ParticleEnum.AOE).radius(1)
            .type(particle)
            .amount(amount));

    }

}
