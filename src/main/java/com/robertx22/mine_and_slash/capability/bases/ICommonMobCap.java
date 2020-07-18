package com.robertx22.mine_and_slash.capability.bases;

import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.MobCaps;
import net.minecraft.nbt.CompoundTag;

public interface ICommonMobCap extends ICommonCap {
    CompoundTag saveToNBT();

    void loadFromNBT(CompoundTag value);

    MobCaps getCapType();

}
