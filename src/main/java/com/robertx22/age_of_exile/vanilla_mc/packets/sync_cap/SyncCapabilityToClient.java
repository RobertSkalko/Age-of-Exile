package com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SyncCapabilityToClient extends MyPacket<SyncCapabilityToClient> {

    public SyncCapabilityToClient() {

    }

    private CompoundNBT nbt;
    private PlayerCaps type;

    public SyncCapabilityToClient(PlayerEntity p, PlayerCaps type) {
        this.nbt = type.getCap(p)
            .saveToNBT();
        this.type = type;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "synccaptoc");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        nbt = tag.readNbt();
        type = tag.readEnum(PlayerCaps.class);

    }

    @Override
    public void saveToData(PacketBuffer tag) {
        try {
            tag.writeNbt(nbt);
            tag.writeEnum(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        final PlayerEntity player = ctx.getPlayer();

        if (player != null) {
            type.getCap(player)
                .loadFromNBT(nbt);

        }
    }

    @Override
    public MyPacket<SyncCapabilityToClient> newInstance() {
        return new SyncCapabilityToClient();
    }
}

