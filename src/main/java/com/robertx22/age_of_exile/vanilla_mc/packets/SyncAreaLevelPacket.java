package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.mmorpg.SyncedToClientValues;
import com.robertx22.library_of_exile.main.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class SyncAreaLevelPacket extends MyPacket<SyncAreaLevelPacket> {

    public int lvl;

    public SyncAreaLevelPacket() {

    }

    public SyncAreaLevelPacket(int lvl) {
        this.lvl = lvl;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "arealvl");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        lvl = tag.readInt();
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeInt(lvl);
    }

    @Override
    public void onReceived(PacketContext ctx) {
        SyncedToClientValues.areaLevel = lvl;
    }

    @Override
    public MyPacket<SyncAreaLevelPacket> newInstance() {
        return new SyncAreaLevelPacket();
    }
}

