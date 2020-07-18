package com.robertx22.mine_and_slash.vanilla_mc.packets.particles;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ParticlePacket {

    private ParticlePacketData data;

    static String LOC = "info";

    public ParticlePacket() {
    }

    public ParticlePacket(ParticlePacketData data) {

        this.data = data;

    }

    public static ParticlePacket decode(PacketByteBuf tag) {

        ParticlePacket newpkt = new ParticlePacket();

        newpkt.data = LoadSave.Load(ParticlePacketData.class, ParticlePacketData.empty(), tag.readCompoundTag(), LOC);

        return newpkt;

    }

    public static void encode(ParticlePacket packet, PacketByteBuf tag) {

        CompoundTag nbt = new CompoundTag();
        LoadSave.Save(packet.data, nbt, LOC);
        tag.writeCompoundTag(nbt);

    }

    public static void handle(final ParticlePacket pkt, Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(() -> {
                try {

                    PlayerEntity p = MMORPG.proxy.getPlayerEntityFromContext(ctx);

                    pkt.data.type.activate(pkt.data, p.world);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        ctx.get()
            .setPacketHandled(true);

    }

}