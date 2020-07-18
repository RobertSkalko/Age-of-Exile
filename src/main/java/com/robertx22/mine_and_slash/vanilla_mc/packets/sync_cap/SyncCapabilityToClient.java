package com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncCapabilityToClient {

    public SyncCapabilityToClient() {

    }

    private CompoundTag nbt;
    private PlayerCaps type;

    public SyncCapabilityToClient(ServerPlayerEntity p, PlayerCaps type) {
        this.nbt = type.getCap(p)
            .saveToNBT();
        this.type = type;
    }

    public static SyncCapabilityToClient decode(PacketByteBuf buf) {

        SyncCapabilityToClient newpkt = new SyncCapabilityToClient();
        newpkt.nbt = buf.readCompoundTag();
        newpkt.type = buf.readEnumConstant(PlayerCaps.class);
        return newpkt;
    }

    public static void encode(SyncCapabilityToClient packet, PacketByteBuf tag) {

        tag.writeCompoundTag(packet.nbt);
        tag.writeEnumConstant(packet.type);

    }

    public static void handle(final SyncCapabilityToClient pkt, Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
            .enqueueWork(() -> {
                try {
                    final PlayerEntity player = MMORPG.proxy.getPlayerEntityFromContext(ctx);

                    if (player != null) {
                        pkt.type.getCap(player)
                            .loadFromNBT(pkt.nbt);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        ctx.get()
            .setPacketHandled(true);

    }

}

