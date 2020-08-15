package com.robertx22.age_of_exile.mobs.slimes;

import com.robertx22.age_of_exile.vanilla_mc.packets.EntityPacket;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class NatureSlime extends MagmaCubeEntity {

    public NatureSlime(EntityType<? extends MagmaCubeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    protected ParticleEffect getParticles() {
        return ParticleTypes.ITEM_SLIME;
    }
}

