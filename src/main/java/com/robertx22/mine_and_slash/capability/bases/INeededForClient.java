package com.robertx22.mine_and_slash.capability.bases;

import net.minecraft.nbt.CompoundTag;

public interface INeededForClient {
    void addClientNBT(CompoundTag nbt);

    void loadFromClientNBT(CompoundTag nbt);
}
