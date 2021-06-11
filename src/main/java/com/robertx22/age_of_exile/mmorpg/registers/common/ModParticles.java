package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {

    public final DefaultParticleType THUNDER = register("thunder", FabricParticleTypes.simple());
    public final DefaultParticleType BUBBLE = register("bubble", FabricParticleTypes.simple());
    public final DefaultParticleType FLAME = register("flame", FabricParticleTypes.simple());
    public final DefaultParticleType POISON = register("poison", FabricParticleTypes.simple());
    public final DefaultParticleType FROST = register("frost", FabricParticleTypes.simple());
    public final DefaultParticleType BLOOD_DRIP = register("blood_drip", FabricParticleTypes.simple());
    public final DefaultParticleType BLOOD_EXPLODE = register("blood_explode", FabricParticleTypes.simple());

    private <T extends ParticleType<?>> T register(String name, T particleType) {
        Registry.PARTICLE_TYPE.register(Registry.PARTICLE_TYPE, new Identifier(Ref.MODID, name), particleType);
        return particleType;
    }

    private <T extends ParticleEffect> ParticleType register(String name, ParticleEffect.Factory<T> deseri) {
        ParticleType<?> particleType = FabricParticleTypes.complex(false, deseri);
        return register(name, particleType);
    }

}
