package com.robertx22.age_of_exile.vanilla_mc.packets.registry;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.text.Text;

public class MyPacketListener implements PacketListener {

    ClientConnection con;

    public MyPacketListener(ClientPlayerEntity con) {
        this.con = con.networkHandler.getConnection();
    }

    @Override
    public void onDisconnected(Text reason) {

    }

    @Override
    public ClientConnection getConnection() {
        return con;
    }
}
