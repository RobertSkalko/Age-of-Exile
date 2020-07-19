package com.robertx22.mine_and_slash.vanilla_mc.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect.Factory;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ParticleDeserializer {

    public static final Factory<EleParticleData> INSTANCE = new Factory<EleParticleData>() {
        @Override
        public EleParticleData read(ParticleType<EleParticleData> data,
                                    StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            String eleName = reader.readString();
            Elements element = Elements.valueOf(eleName);
            reader.expect(' ');
            Identifier key = new Identifier(reader.readString());
            ParticleType<?> type = Registry.PARTICLE_TYPE.get(key);

            return new EleParticleData(type, element);
        }

        @Override
        public EleParticleData read(ParticleType<EleParticleData> data,
                                    PacketByteBuf buf) {
            String eleName = buf.readString();
            Elements element = Elements.valueOf(eleName);

            Identifier key = new Identifier(buf.readString());
            ParticleType<?> type = Registry.PARTICLE_TYPE.get(key);

            return new EleParticleData(type, element);
        }
    };
}
