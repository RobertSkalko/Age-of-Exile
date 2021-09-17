package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.particles.SimpleParticle;
import net.fabricmc.fabric.impl.client.particle.ParticleFactoryRegistryImpl;
import net.minecraft.client.particle.WaterBubbleParticle;

public class ParticleFactoryRegister {

    public static void register() {

        ParticleFactoryRegistryImpl.INSTANCE.register(ModRegistry.PARTICLES.BUBBLE, WaterBubbleParticle.Factory::new);
        ParticleFactoryRegistryImpl.INSTANCE.register(ModRegistry.PARTICLES.FLAME, s -> new SimpleParticle.Factory(5, s));
        ParticleFactoryRegistryImpl.INSTANCE.register(ModRegistry.PARTICLES.POISON, s -> new SimpleParticle.Factory(8, s));
        ParticleFactoryRegistryImpl.INSTANCE.register(ModRegistry.PARTICLES.FROST, s -> new SimpleParticle.Factory(8, s));

        ParticleFactoryRegistryImpl.INSTANCE.register(ModRegistry.PARTICLES.BLOOD_DRIP, s -> new SimpleParticle.Factory(8, s));

        MMORPG.devToolsLog("Registered Particles");
    }

}
