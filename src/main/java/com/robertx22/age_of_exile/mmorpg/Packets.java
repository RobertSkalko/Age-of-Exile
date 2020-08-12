package com.robertx22.age_of_exile.mmorpg;

import com.robertx22.age_of_exile.vanilla_mc.packets.MyPacket;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

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

    public static <T> void registerClientToServerPacket(MyPacket<T> packet) {
        ServerSidePacketRegistry.INSTANCE.register(packet.getIdentifier(), packet);
    }

    public static <T> void registerServerToClient(MyPacket<T> packet) {
        ClientSidePacketRegistry.INSTANCE.register(packet.getIdentifier(), packet);
    }

}
