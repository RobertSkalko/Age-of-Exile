package com.robertx22.mine_and_slash.mmorpg;

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;

public class Packets {

    public static void sendToClient(PlayerEntity player, Packet<?> packet) {
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, packet);

    }

    public static void sendToServer(Packet<?> packet) {
        ClientSidePacketRegistry.INSTANCE.sendToServer(packet);

    }

}
