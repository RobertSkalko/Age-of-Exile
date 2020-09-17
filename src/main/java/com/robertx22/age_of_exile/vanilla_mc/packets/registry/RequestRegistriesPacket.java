package com.robertx22.age_of_exile.vanilla_mc.packets.registry;

import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SyncTime;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.packets.OnLoginClientPacket;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class RequestRegistriesPacket extends MyPacket<RequestRegistriesPacket> {
    SyncTime sync;

    public RequestRegistriesPacket(SyncTime sync) {
        this.sync = sync;
    }

    public RequestRegistriesPacket() {

    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "req_reqs");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        sync = SyncTime.valueOf(tag.readString(30));
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(sync.name(), 30);
    }

    @Override
    public void onReceived(PacketContext ctx) {
        SlashRegistry.sendPacketsToClient((ServerPlayerEntity) ctx.getPlayer(), sync);
        Packets.sendToClient(ctx.getPlayer(), new OnLoginClientPacket(sync, OnLoginClientPacket.When.AFTER));
    }

    @Override
    public MyPacket<RequestRegistriesPacket> newInstance() {
        return new RequestRegistriesPacket();
    }

}
