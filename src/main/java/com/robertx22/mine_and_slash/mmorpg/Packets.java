package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.vanilla_mc.packets.MyPacket;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.PacketByteBuf;

public class Packets {

    public static <T> void sendToClient(PlayerEntity player, MyPacket<T> packet) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        packet.saveToData(buf);
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, packet.getIdentifier(), buf);

    }

    public static <T> void sendToServer(MyPacket<T> packet) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        packet.saveToData(buf);
        ClientSidePacketRegistry.INSTANCE.sendToServer(packet.getIdentifier(), buf);
    }

}
