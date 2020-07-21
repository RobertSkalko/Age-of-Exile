package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.mine_and_slash.mmorpg.EntityPacket;
import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.vanilla_mc.packets.*;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticlePacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

public class ClientPacketRegister {

    public static void register() {

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
