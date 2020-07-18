package com.robertx22.mine_and_slash.capability.bases;

import net.minecraft.nbt.CompoundTag;

public interface ICommonCap {
    CompoundTag saveToNBT();

    void loadFromNBT(CompoundTag value);

}
