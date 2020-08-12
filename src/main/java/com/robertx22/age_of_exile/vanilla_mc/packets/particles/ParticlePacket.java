package com.robertx22.age_of_exile.vanilla_mc.packets.particles;

import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.vanilla_mc.packets.MyPacket;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

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