package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.vanilla_mc.particles.MyBubbleParticle;
import net.fabricmc.fabric.impl.client.particle.ParticleFactoryRegistryImpl;
import net.minecraft.client.particle.WaterBubbleParticle;

public class ParticleFactoryRegister {

    public static void register() {

        ParticleFactoryRegistryImpl.INSTANCE.register(ModRegistry.PARTICLES.BUBBLE, WaterBubbleParticle.Factory::new);
        ParticleFactoryRegistryImpl.INSTANCE.register(ModRegistry.PARTICLES.THUNDER, MyBubbleParticle.Factory::new);

        MMORPG.devToolsLog("Registered Particles");
    }

}
