package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.vanilla_mc.particles.EleParticleData;
import com.robertx22.mine_and_slash.vanilla_mc.particles.ParticleDeserializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {

    public final ParticleType<EleParticleData> DRIP = register("drip", ParticleDeserializer.INSTANCE);
    public final DefaultParticleType THUNDER = register("thunder", FabricParticleTypes.simple());
    public final DefaultParticleType BUBBLE = register("bubble", FabricParticleTypes.simple());

    private <T extends ParticleType<?>> T register(String name, T particleType) {
        Registry.PARTICLE_TYPE.register(Registry.PARTICLE_TYPE, new Identifier(Ref.MODID, name), particleType);
        return particleType;
    }

    private <T extends ParticleEffect> ParticleType register(String name, ParticleEffect.Factory<T> deseri) {
        ParticleType<?> particleType = FabricParticleTypes.complex(false, deseri);
        return register(name, particleType);
    }

}
