package com.robertx22.mine_and_slash.vanilla_mc.packets;

import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public abstract class MyPacket<T> implements PacketConsumer {

    public abstract T fromData(PacketByteBuf buf);

    public abstract PacketByteBuf toData(T t);

    public abstract void onReceived(PacketContext ctx, T data);

    public abstract Identifier getIdentifier();

    @Override
    public void accept(PacketContext ctx, PacketByteBuf buf) {

        ctx.getTaskQueue()
            .execute(() -> {
                try {
                    T data = fromData(buf);
                    this.onReceived(ctx, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }
}
