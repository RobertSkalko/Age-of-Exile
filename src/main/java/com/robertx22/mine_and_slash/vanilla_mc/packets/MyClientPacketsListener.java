package com.robertx22.mine_and_slash.vanilla_mc.packets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.text.Text;

public class MyClientPacketsListener implements PacketListener {

    private MinecraftClient client = MinecraftClient.getInstance();

    public void onDamageNumPacket(DmgNumPacket pkt) {
        NetworkThreadUtils.forceMainThread(pkt, this, client);
    }

    @Override
    public void onDisconnected(Text reason) {

    }

    @Override
    public ClientConnection getConnection() {
        return MinecraftClient.getInstance().player.networkHandler.getConnection();
    }
}
