package com.robertx22.age_of_exile.capability.bases;

import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.MobCaps;
import net.minecraft.nbt.CompoundTag;

public interface ICommonMobCap extends ICommonCap {
    CompoundTag toTag();

    void fromTag(CompoundTag value);

    MobCaps getCapType();

}
