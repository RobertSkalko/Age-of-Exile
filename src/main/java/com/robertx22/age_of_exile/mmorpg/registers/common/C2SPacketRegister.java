package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.Packets;
import com.robertx22.age_of_exile.vanilla_mc.packets.*;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.CastSpellPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.HotbarSetupPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;

public class C2SPacketRegister {

    public static void register() {

        Packets.registerClientToServerPacket(new ModifyItemPacket());
        Packets.registerClientToServerPacket(new RequestTilePacket());
        Packets.registerClientToServerPacket(new RequestSyncCapToClient());
        Packets.registerClientToServerPacket(new SpendStatPointsPacket());
        Packets.registerClientToServerPacket(new HotbarSetupPacket());
        Packets.registerClientToServerPacket(new CastSpellPacket());
        Packets.registerClientToServerPacket(new OpenSpellSetupContainerPacket());
        Packets.registerClientToServerPacket(new RequestAreaSyncPacket());

    }

}


