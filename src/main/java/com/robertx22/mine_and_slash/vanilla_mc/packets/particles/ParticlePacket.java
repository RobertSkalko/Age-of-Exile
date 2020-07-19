package com.robertx22.mine_and_slash.vanilla_mc.packets.particles;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import com.robertx22.mine_and_slash.vanilla_mc.packets.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class ParticlePacket extends MyPacket<ParticlePacket> {

    private ParticlePacketData data;

    static String LOC = "info";

    public ParticlePacket() {
    }

    public ParticlePacket(ParticlePacketData data) {

        this.data = data;

    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "particle");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        data = LoadSave.Load(ParticlePacketData.class, ParticlePacketData.empty(), tag.readCompoundTag(), LOC);

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        CompoundTag nbt = new CompoundTag();
        LoadSave.Save(data, nbt, LOC);
        tag.writeCompoundTag(nbt);

    }

    @Override
    public void onReceived(PacketContext ctx) {

        data.type.activate(data, ctx.getPlayer().world);

    }

    @Override
    public MyPacket<ParticlePacket> newInstance() {
        return new ParticlePacket();
    }
}