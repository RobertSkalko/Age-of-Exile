package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.mmorpg.EntityPacket;
import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.vanilla_mc.packets.DmgNumPacket;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

public class PacketRegister {

    public static void register() {

        Packets.registerServerToClient(new DmgNumPacket());

        ClientSidePacketRegistry.INSTANCE.register(EntityPacket.ID, (ctx, buf) -> {
            EntityPacket.onPacket(ctx, buf);
        });
    }

}


