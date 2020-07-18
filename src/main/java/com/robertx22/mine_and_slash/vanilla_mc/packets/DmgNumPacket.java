package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.Packet;
import net.minecraft.util.PacketByteBuf;

import java.io.IOException;

public class DmgNumPacket implements Packet<MyClientPacketsListener> {

    public String element;
    public String string;
    public double x;
    public double y;
    public double z;
    public float height;
    public boolean isExp;
    public float number;

    private DmgNumPacket() {

    }

    public DmgNumPacket(LivingEntity entity, Elements ele, String str, float number) {

        element = ele.toString();
        string = str;
        x = entity.getX();
        y = entity.getY();
        z = entity.getZ();
        height = entity.getHeight();
        this.number = number;

    }

    @Override
    public void read(PacketByteBuf tag) throws IOException {
        element = tag.readString();
        tag.readDouble();
        tag.readDouble();
        tag.readDouble();
        tag.readFloat();
        tag.readBoolean();
        tag.readString();
        tag.readFloat();
    }

    @Override
    public void write(PacketByteBuf tag) throws IOException {
        tag.writeString(element);
        tag.writeDouble(x);
        tag.writeDouble(y);
        tag.writeDouble(z);
        tag.writeFloat(height);
        tag.writeBoolean(isExp);
        tag.writeString(string);
        tag.writeFloat(number);
    }

    @Override
    public void apply(MyClientPacketsListener listener) {
        listener.onDamageNumPacket(this);
    }

}
