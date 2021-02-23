package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.database.registry.RegistryPackets;
import com.robertx22.age_of_exile.database.registry.SyncTime;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class OnLoginClientPacket extends MyPacket<OnLoginClientPacket> {

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "onlogin");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        when = When.valueOf(tag.readString(10));
        sync = SyncTime.valueOf(tag.readString(30));
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeString(when.name(), 10);
        tag.writeString(sync.name(), 30);
    }

    @Override
    public void onReceived(PacketContext ctx) {

        if (when == When.BEFORE) {
        }
        if (when == When.AFTER) {
            RegistryPackets.registerAll(sync);
        }

    }

    @Override
    public MyPacket<OnLoginClientPacket> newInstance() {
        return new OnLoginClientPacket();
    }

    public enum When {
        BEFORE, AFTER
    }

    public When when;
    SyncTime sync;

    public OnLoginClientPacket(SyncTime sync, When when) {
        this.when = when;
        this.sync = sync;
    }

    public OnLoginClientPacket() {

    }
}