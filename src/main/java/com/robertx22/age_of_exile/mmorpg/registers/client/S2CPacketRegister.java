package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.vanilla_mc.packets.*;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellClientEntityIsCastingSpellPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellClientToCastSpellPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import com.robertx22.library_of_exile.main.Packets;

public class S2CPacketRegister {

    public static void register() {

        Packets.registerServerToClient(new DmgNumPacket());
        Packets.registerServerToClient(new ForceChoosingRace());
        Packets.registerServerToClient(new EfficientMobUnitPacket());
        Packets.registerServerToClient(new EntityUnitPacket());
        Packets.registerServerToClient(new NoManaPacket());
        Packets.registerServerToClient(new OpenGuiPacket());
        Packets.registerServerToClient(new SyncCapabilityToClient());
        Packets.registerServerToClient(new TellClientToCastSpellPacket());
        Packets.registerServerToClient(new SyncAreaLevelPacket());
        Packets.registerServerToClient(new SkillLevelUpToClient());
        Packets.registerServerToClient(new TellClientEntityIsCastingSpellPacket());

    }
}
