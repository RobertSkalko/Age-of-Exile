package com.robertx22.mine_and_slash.vanilla_mc.particles;

import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;

public class EleParticleData extends ParticleType<EleParticleData> implements ParticleEffect {

    Elements element;
    ParticleType<?> type;

    public EleParticleData(ParticleType<?> type, Elements element) {
        super(true, ParticleDeserializer.INSTANCE);
        this.element = element;
        this.type = type;
    }

    @Override
    public ParticleType<?> getType() {
        return type;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeString(element.name());
        buf.writeString(Registry.PARTICLE_TYPE.getId(type)
            .toString());
    }

    @Override
    public String asString() {
        return "";
    }
}
