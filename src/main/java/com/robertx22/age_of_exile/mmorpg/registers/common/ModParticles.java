package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ModParticles {

    public final BasicParticleType BUBBLE = register("bubble", FabricParticleTypes.simple());
    public final BasicParticleType FLAME = register("flame", FabricParticleTypes.simple());
    public final BasicParticleType POISON = register("poison", FabricParticleTypes.simple());
    public final BasicParticleType FROST = register("frost", FabricParticleTypes.simple());
    public final BasicParticleType BLOOD_DRIP = register("blood_drip", FabricParticleTypes.simple());

    private <T extends ParticleType<?>> T register(String name, T particleType) {
        Registry.PARTICLE_TYPE.register(Registry.PARTICLE_TYPE, new ResourceLocation(SlashRef.MODID, name), particleType);
        return particleType;
    }

    private <T extends ParticleOptions> ParticleType register(String name, ParticleOptions.Deserializer<T> deseri) {
        ParticleType<?> particleType = FabricParticleTypes.complex(false, deseri);
        return register(name, particleType);
    }

}
