package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.dimension.packets.StartDelveMapPacket;
import com.robertx22.age_of_exile.dimension.packets.StartDungeonPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.AllocateStatPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.ModifyItemPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.OpenGuiPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.perks.PerkChangePacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCancelSpellCast;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCastSpellPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.library_of_exile.main.Packets;

public class C2SPacketRegister {

    public static void register() {

        Packets.registerClientToServerPacket(new StartDelveMapPacket());
        Packets.registerClientToServerPacket(new StartDungeonPacket());
        Packets.registerClientToServerPacket(new ModifyItemPacket());
        Packets.registerClientToServerPacket(new RequestSyncCapToClient());
        Packets.registerClientToServerPacket(new TellServerToCastSpellPacket());
        Packets.registerClientToServerPacket(new PerkChangePacket());
        Packets.registerClientToServerPacket(new AllocateStatPacket());
        Packets.registerClientToServerPacket(new OpenGuiPacket());
        Packets.registerClientToServerPacket(new TellServerToCancelSpellCast());

    }

}


