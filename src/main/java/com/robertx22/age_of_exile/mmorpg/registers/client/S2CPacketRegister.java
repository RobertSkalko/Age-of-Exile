package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.vanilla_mc.packets.*;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellClientEntityIsCastingSpellPacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellClientToCastSpellPacket;
import com.robertx22.library_of_exile.main.Packets;

public class S2CPacketRegister {

    public static void register() {
        int i = 1000;

        Packets.registerServerToClient(MMORPG.NETWORK, new SendActionToClient(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new DmgNumPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new EfficientMobUnitPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new EntityUnitPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new NoManaPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new OpenGuiPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new TellClientToCastSpellPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new SyncAreaLevelPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new SkillLevelUpToClient(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new TellClientEntityIsCastingSpellPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new TotemAnimationPacket(), i++);

    }
}
