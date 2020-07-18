package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.vanilla_mc.packets.*;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticlePacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.CastSpellPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.HotbarSetupPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PacketRegister {

    static int index = 0;

    // bit shorter
    private static <MSG> void reg(Class<MSG> messageType, BiConsumer<MSG, PacketByteBuf> encoder,
                                  Function<PacketByteBuf, MSG> decoder,
                                  BiConsumer<MSG, Supplier<NetworkEvent.Context>> messageConsumer) {

        MMORPG.Network.registerMessage(index++, messageType, encoder, decoder, messageConsumer);

    }

    public static void register() {

        reg(EntityUnitPacket.class, EntityUnitPacket::encode, EntityUnitPacket::decode, EntityUnitPacket::handle);

        reg(DmgNumPacket.class, DmgNumPacket::encode, DmgNumPacket::decode, DmgNumPacket::handle);

        reg(NoManaPacket.class, NoManaPacket::encode, NoManaPacket::decode, NoManaPacket::handle);

        reg(RequestTilePacket.class, RequestTilePacket::encode, RequestTilePacket::decode, RequestTilePacket::handle);

        reg(SyncCapabilityToClient.class, SyncCapabilityToClient::encode, SyncCapabilityToClient::decode,
            SyncCapabilityToClient::handle
        );

        reg(RequestSyncCapToClient.class, RequestSyncCapToClient::encode, RequestSyncCapToClient::decode,
            RequestSyncCapToClient::handle
        );

        reg(OpenGuiPacket.class, OpenGuiPacket::encode, OpenGuiPacket::decode, OpenGuiPacket::handle);

        reg(EfficientMobUnitPacket.class, EfficientMobUnitPacket::encode, EfficientMobUnitPacket::decode,
            EfficientMobUnitPacket::handle
        );

        reg(SyncConfigToClientPacket.class, SyncConfigToClientPacket::encode, SyncConfigToClientPacket::decode,
            SyncConfigToClientPacket::handle
        );

        reg(CastSpellPacket.class, CastSpellPacket::encode, CastSpellPacket::decode, CastSpellPacket::handle);

        reg(HotbarSetupPacket.class, HotbarSetupPacket::encode, HotbarSetupPacket::decode, HotbarSetupPacket::handle);

        reg(ParticlePacket.class, ParticlePacket::encode, ParticlePacket::decode, ParticlePacket::handle);

        reg(RegistryPacket.class, RegistryPacket::encode, RegistryPacket::decode, RegistryPacket::handle);

        reg(
            OnLoginClientPacket.class, OnLoginClientPacket::encode, OnLoginClientPacket::decode,
            OnLoginClientPacket::handle
        );

        reg(RarityPacket.class, RarityPacket::encode, RarityPacket::decode, RarityPacket::handle);

        reg(SpendStatPointsPacket.class, SpendStatPointsPacket::encode, SpendStatPointsPacket::decode, SpendStatPointsPacket::handle);

    }

}


