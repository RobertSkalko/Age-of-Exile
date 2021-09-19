package com.robertx22.age_of_exile.capability.bases;

import net.minecraft.nbt.CompoundNBT;

public interface INeededForClient {
    void addClientNBT(CompoundNBT nbt);

    void loadFromClientNBT(CompoundNBT nbt);
}
