package com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestSyncCapToClient {

    public RequestSyncCapToClient() {

    }

    private PlayerCaps type;

    public RequestSyncCapToClient(PlayerCaps type) {
        this.type = type;
    }

    public static RequestSyncCapToClient decode(PacketByteBuf buf) {

        RequestSyncCapToClient newpkt = new RequestSyncCapToClient();
        newpkt.type = buf.readEnumConstant(PlayerCaps.class);
        return newpkt;
    }

    public static void encode(RequestSyncCapToClient packet, PacketByteBuf tag) {

        tag.writeEnumConstant(packet.type);

    }

    public static void handle(final RequestSyncCapToClient pkt, Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(() -> {
                try {

                    final ServerPlayerEntity player = ctx.get()
                        .getSender();

                    if (player != null) {
                        MMORPG.sendToClient(new SyncCapabilityToClient(player, pkt.type), player);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        ctx.get()
            .setPacketHandled(true);

    }

}
