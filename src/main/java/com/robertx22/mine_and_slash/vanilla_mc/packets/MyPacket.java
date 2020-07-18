package com.robertx22.mine_and_slash.vanilla_mc.packets;

import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public abstract class MyPacket<T> implements PacketConsumer {

    public abstract Identifier getIdentifier();

    public abstract void loadFromData(PacketByteBuf tag);

    public abstract void saveToData(PacketByteBuf tag);

    public abstract void onReceived(PacketContext ctx);

    public abstract MyPacket<T> newInstance();

    @Override
    public void accept(PacketContext ctx, PacketByteBuf buf) {

        ctx.getTaskQueue()
            .execute(() -> {
                try {
                    MyPacket<T> data = newInstance();
                    data.loadFromData(buf);
                    this.onReceived(ctx);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }
}
