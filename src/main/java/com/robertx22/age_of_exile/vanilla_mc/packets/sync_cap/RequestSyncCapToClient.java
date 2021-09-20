package com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class RequestSyncCapToClient extends MyPacket<RequestSyncCapToClient> {

    public RequestSyncCapToClient() {

    }

    private PlayerCaps type;

    public RequestSyncCapToClient(PlayerCaps type) {
        this.type = type;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "reqdata");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        type = tag.readEnum(PlayerCaps.class);
    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeEnum(type);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Packets.sendToClient(ctx.getPlayer(), new SyncCapabilityToClient(ctx.getPlayer(), type));
    }

    @Override
    public MyPacket<RequestSyncCapToClient> newInstance() {
        return new RequestSyncCapToClient();
    }
}
