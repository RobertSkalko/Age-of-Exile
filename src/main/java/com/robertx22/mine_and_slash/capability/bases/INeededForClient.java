package com.robertx22.mine_and_slash.capability.bases;

import net.minecraft.nbt.CompoundTag;

public interface INeededForClient {
    CompoundTag getClientNBT();

    void setClientNBT(CompoundTag value);
}
