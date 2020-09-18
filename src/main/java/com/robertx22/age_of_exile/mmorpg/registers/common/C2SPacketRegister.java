package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.vanilla_mc.packets.ModifyItemPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.SpendStatPointsPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.perks.PerkChangePacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.registry.RequestRegistriesPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.HotbarSetupPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCastSpellPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.library_of_exile.main.Packets;

public class C2SPacketRegister {

    public static void register() {

        Packets.registerClientToServerPacket(new ModifyItemPacket());
        Packets.registerClientToServerPacket(new RequestSyncCapToClient());
        Packets.registerClientToServerPacket(new SpendStatPointsPacket());
        Packets.registerClientToServerPacket(new HotbarSetupPacket());
        Packets.registerClientToServerPacket(new TellServerToCastSpellPacket());
        Packets.registerClientToServerPacket(new PerkChangePacket());
        Packets.registerClientToServerPacket(new RequestRegistriesPacket());

    }

}


