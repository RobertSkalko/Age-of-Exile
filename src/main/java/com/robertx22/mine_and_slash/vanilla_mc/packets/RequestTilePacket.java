package com.robertx22.mine_and_slash.vanilla_mc.packets;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestTilePacket {

    public BlockPos pos;

    public RequestTilePacket() {

    }

    public RequestTilePacket(BlockPos pos) {
        this.pos = pos;
    }

    public static RequestTilePacket decode(PacketByteBuf buf) {

        RequestTilePacket newpkt = new RequestTilePacket();

        newpkt.pos = buf.readBlockPos();

        return newpkt;

    }

    public static void encode(RequestTilePacket packet, PacketByteBuf tag) {

        tag.writeBlockPos(packet.pos);

    }

    public static void handle(final RequestTilePacket pkt, Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(() -> {
                try {

                    ServerPlayerEntity player = ctx.get()
                        .getSender();

                    if (player != null) {
                        sendUpdate(pkt.pos, player);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        ctx.get()
            .setPacketHandled(true);

    }

    private static void sendUpdate(BlockPos pos, ServerPlayerEntity player) {

        BlockEntity tile = player.world.getBlockEntity(pos);

        if (tile != null) {

            BlockEntityUpdateS2CPacket supdatetileentitypacket = tile.toUpdatePacket();
            if (supdatetileentitypacket != null) {
                player.networkHandler.sendPacket(supdatetileentitypacket);
            }
        }

    }

}
