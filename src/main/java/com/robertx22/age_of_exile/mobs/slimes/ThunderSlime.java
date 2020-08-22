package com.robertx22.age_of_exile.mobs.slimes;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;

public class ThunderSlime extends BaseSlime {

    public ThunderSlime(EntityType<? extends MagmaCubeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ParticleEffect getParticles() {
        return ModRegistry.PARTICLES.THUNDER;
    }
}


