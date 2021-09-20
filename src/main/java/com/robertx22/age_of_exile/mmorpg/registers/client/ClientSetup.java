package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.registers.common.ModParticles;
import com.robertx22.age_of_exile.vanilla_mc.particles.SimpleParticle;
import com.robertx22.library_of_exile.main.ForgeEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.BubbleParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;

public class ClientSetup {

    public static void setup() {

        RenderLayersRegister.setup();
        ContainerGuiRegisters.reg();
        RenderRegister.regRenders();
        S2CPacketRegister.register();

        ForgeEvents.registerForgeEvent(ParticleFactoryRegisterEvent.class, event -> {
            ParticleManager man = Minecraft.getInstance().particleEngine;

            man.register(ModParticles.BUBBLE.get(), BubbleParticle.Factory::new);
            man.register(ModParticles.FLAME.get(), s -> new SimpleParticle.Factory(5, s));
            man.register(ModParticles.POISON.get(), s -> new SimpleParticle.Factory(8, s));
            man.register(ModParticles.FROST.get(), s -> new SimpleParticle.Factory(8, s));
            man.register(ModParticles.BLOOD_DRIP.get(), s -> new SimpleParticle.Factory(8, s));

        });

    }
}
