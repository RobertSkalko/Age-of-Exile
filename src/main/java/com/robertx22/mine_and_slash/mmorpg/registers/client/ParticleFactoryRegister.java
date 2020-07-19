package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.vanilla_mc.particles.DripEleParticle;
import com.robertx22.mine_and_slash.vanilla_mc.particles.MyBubbleParticle;
import net.fabricmc.fabric.impl.client.particle.ParticleFactoryRegistryImpl;
import net.minecraft.client.particle.BubbleColumnUpParticle;

public class ParticleFactoryRegister {

    public static void register() {

        ParticleFactoryRegistryImpl.INSTANCE.register(ModRegistry.PARTICLES.DRIP, DripEleParticle.DrippingElementalFactory::new);
        ParticleFactoryRegistryImpl.INSTANCE.register(ModRegistry.PARTICLES.BUBBLE, BubbleColumnUpParticle.Factory::new);
        ParticleFactoryRegistryImpl.INSTANCE.register(ModRegistry.PARTICLES.THUNDER, MyBubbleParticle.Factory::new);

        MMORPG.devToolsLog("Registered Particles");
    }

}
