package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;


import java.util.UUID;

public class ClientOnly {

    @Nullable
    public static Entity getEntityByUUID(World world, UUID id) {

        if (world instanceof ClientWorld) {
            for (Entity entity : ((ClientWorld) world).getEntities()) {
                if (entity.getUuid()
                    .equals(id)) {

                    return entity;
                }
            }
        }
        return null;

    }

    public static void bossParticle(LivingEntity ent, ParticleEffect p) {
        MinecraftClient.getInstance().worldRenderer.addParticle(p, true,
            ent.x + (ent.world.random.nextDouble() - 0.5D) * (double) ent
                .getWidth(),
            ent.y + ent.world.random.nextDouble() * (double) ent.getHeight() - 0.25D,
            ent.z + (ent.world.random.nextDouble() - 0.5D) * (double) ent
                .getWidth(),
            (ent.world.random.nextDouble() - 0.5D) * 2.0D,
            -ent.world.random.nextDouble(),
            (ent.world.random.nextDouble() - 0.5D) * 2.0D
        );
    }

    public static PlayerEntity getPlayer() {
        return MinecraftClient.getInstance().player;
    }
}
