package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.vanilla_mc.particles.EleParticleData;
import com.robertx22.mine_and_slash.vanilla_mc.particles.ParticleDeserializer;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Ref.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRegister {

    private final static String eleId = Ref.MODID + ":drip";
    private final static String thunderId = Ref.MODID + ":thunder";
    private final static String bubbleID = Ref.MODID + ":bubble";

    @ObjectHolder(eleId)
    public static final ParticleType<EleParticleData> DRIP = null;

    @ObjectHolder(thunderId)
    public static final DefaultParticleType THUNDER = null;

    @ObjectHolder(bubbleID)
    public static final DefaultParticleType BUBBLE = null;

    @SubscribeEvent
    public static void register(RegistryEvent.Register<ParticleType<?>> event) {

        register(eleId, ParticleDeserializer.INSTANCE);
        register(thunderId, new DefaultParticleType(false));
        register(bubbleID, new DefaultParticleType(false));

    }

    private static <T extends ParticleType<?>> T register(String name, T particleType) {
        particleType.setRegistryName(name);
        ForgeRegistries.PARTICLE_TYPES.register(particleType);
        return particleType;
    }

    private static <T extends ParticleEffect> ParticleType register(String name, ParticleEffect.Factory<T> deseri) {
        ParticleType<?> particleType = new ParticleType(false, deseri);
        return register(name, particleType);
    }

}
