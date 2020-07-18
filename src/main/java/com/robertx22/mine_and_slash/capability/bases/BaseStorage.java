package com.robertx22.mine_and_slash.capability.bases;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.math.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class BaseStorage<TYPE extends ICommonCap> implements Capability.IStorage<TYPE> {

    @Override
    public Tag writeNBT(Capability<TYPE> capability, TYPE instance, Direction side) {

        return instance.saveToNBT();
    }

    @Override
    public void readNBT(Capability<TYPE> capability, TYPE instance, Direction side, Tag nbt) {

        instance.loadFromNBT((CompoundTag) nbt);

    }

}