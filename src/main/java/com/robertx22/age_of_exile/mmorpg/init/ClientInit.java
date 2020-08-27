package com.robertx22.age_of_exile.mmorpg.init;

import com.robertx22.age_of_exile.capability.world.WorldAreas;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Packets;
import com.robertx22.age_of_exile.mmorpg.event_registers.Client;
import com.robertx22.age_of_exile.mmorpg.registers.client.ClientSetup;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.age_of_exile.mmorpg.registers.client.ParticleFactoryRegister;
import com.robertx22.age_of_exile.vanilla_mc.packets.RequestAreaSyncPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.minecraft.util.math.BlockPos;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ClientSetup.setup();

        KeybindsRegister.register();
        Client.register();

        ParticleFactoryRegister.register();

        ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
            BlockPos pos = chunk.getPos()
                .getCenterBlockPos();
            WorldAreas areas = ModRegistry.COMPONENTS.WORLD_AREAS.get(world);

            if (!areas.hasArea(pos)) {
                Packets.sendToServer(new RequestAreaSyncPacket(pos));
            }
        });
    }
}
