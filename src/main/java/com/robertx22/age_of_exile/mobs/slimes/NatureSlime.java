package com.robertx22.age_of_exile.mobs.slimes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class NatureSlime extends BaseSlime {

    public NatureSlime(EntityType<? extends MagmaCubeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ParticleEffect getParticles() {
        return ParticleTypes.ITEM_SLIME;
    }
}

