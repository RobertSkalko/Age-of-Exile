package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.dimension.packets.StartDelveMapPacket;
import com.robertx22.age_of_exile.dimension.packets.StartDungeonPacket;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.vanilla_mc.packets.AllocateSpellPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.AllocateStatPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.ModifyItemPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.perks.PerkChangePacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.AllocateSynergyPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.SetupHotbarPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCancelSpellCast;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCastSpellPacket;
import com.robertx22.library_of_exile.main.Packets;

public class C2SPacketRegister {

    public static void register() {

        int i = 100;
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new StartDelveMapPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new StartDungeonPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new ModifyItemPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new TellServerToCastSpellPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new PerkChangePacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new AllocateSpellPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new AllocateSynergyPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new AllocateStatPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new TellServerToCancelSpellCast(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new SetupHotbarPacket(), i++);
    }

}


