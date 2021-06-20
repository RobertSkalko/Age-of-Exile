package com.robertx22.age_of_exile.capability.bases;

import net.minecraft.nbt.NbtCompound;

public interface INeededForClient {
    void addClientNBT(NbtCompound nbt);

    void loadFromClientNBT(NbtCompound nbt);
}
