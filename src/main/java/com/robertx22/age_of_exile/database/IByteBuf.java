package com.robertx22.age_of_exile.database;

import net.minecraft.network.PacketByteBuf;

public interface IByteBuf<T> {

    T getFromBuf(PacketByteBuf buf);

    void toBuf(PacketByteBuf buf);
}
