package com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap;

import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.vanilla_mc.packets.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class RequestSyncCapToClient extends MyPacket<RequestSyncCapToClient> {

    public RequestSyncCapToClient() {

    }

    private PlayerCaps type;

    public RequestSyncCapToClient(PlayerCaps type) {
        this.type = type;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "reqdata");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        type = tag.readEnumConstant(PlayerCaps.class);

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeEnumConstant(type);

    }

    @Override
    public void onReceived(PacketContext ctx) {
        Packets.sendToClient(ctx.getPlayer(), new SyncCapabilityToClient(ctx.getPlayer(), type));
    }

    @Override
    public MyPacket<RequestSyncCapToClient> newInstance() {
        return new RequestSyncCapToClient();
    }
}
