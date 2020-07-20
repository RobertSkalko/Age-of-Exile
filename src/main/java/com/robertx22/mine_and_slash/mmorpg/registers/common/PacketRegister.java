package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.mmorpg.EntityPacket;
import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.vanilla_mc.packets.*;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticlePacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.CastSpellPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.HotbarSetupPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

public class PacketRegister {

    public static void register() {

        Packets.registerClientToServerPacket(new RequestTilePacket());
        Packets.registerClientToServerPacket(new RequestSyncCapToClient());
        Packets.registerClientToServerPacket(new SpendStatPointsPacket());
        Packets.registerClientToServerPacket(new HotbarSetupPacket());
        Packets.registerClientToServerPacket(new CastSpellPacket());

        Packets.registerServerToClient(new DmgNumPacket());
        Packets.registerServerToClient(new EfficientMobUnitPacket());
        Packets.registerServerToClient(new EntityUnitPacket());
        Packets.registerServerToClient(new NoManaPacket());
        Packets.registerServerToClient(new OnLoginClientPacket());
        Packets.registerServerToClient(new OpenGuiPacket());
        Packets.registerServerToClient(new RarityPacket());
        Packets.registerServerToClient(new RegistryPacket());
        Packets.registerServerToClient(new SyncConfigToClientPacket());
        Packets.registerServerToClient(new ParticlePacket());
        Packets.registerServerToClient(new SyncCapabilityToClient());

        ClientSidePacketRegistry.INSTANCE.register(EntityPacket.ID, (ctx, buf) -> {
            EntityPacket.onPacket(ctx, buf);
        });
    }

}


