package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.vanilla_mc.packets.DmgNumPacket;

public class PacketRegister {

    public static void register() {

        Packets.registerServerToClient(new DmgNumPacket());

    }

}


