package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SpendStatPointsPacket {

    public String stat;

    public SpendStatPointsPacket() {

    }

    public SpendStatPointsPacket(Stat stat) {
        this.stat = stat.GUID();

    }

    public static SpendStatPointsPacket decode(PacketByteBuf buf) {

        SpendStatPointsPacket newpkt = new SpendStatPointsPacket();

        newpkt.stat = buf.readString(30);

        return newpkt;

    }

    public static void encode(SpendStatPointsPacket packet, PacketByteBuf tag) {

        tag.writeString(packet.stat, 30);

    }

    public static void handle(final SpendStatPointsPacket pkt, Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(() -> {
                try {
                    ServerPlayerEntity player = ctx.get()
                        .getSender();
                    if (player != null) {
                        Load.statPoints(player)
                            .addPoint(player, SlashRegistry.Stats()
                                .get(pkt.stat), Load.Unit(player));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        ctx.get()
            .setPacketHandled(true);

    }

}