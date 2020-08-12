package com.robertx22.age_of_exile.capability.bases;

import net.minecraft.nbt.CompoundTag;

public interface INeededForClient {
    void addClientNBT(CompoundTag nbt);

    void loadFromClientNBT(CompoundTag nbt);
}
