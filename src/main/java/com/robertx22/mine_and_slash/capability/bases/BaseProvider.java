package com.robertx22.mine_and_slash.capability.bases;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;



public abstract class BaseProvider<TYPE> implements ICapabilitySerializable<CompoundTag> {

    public abstract TYPE defaultImpl();

    public abstract Capability<TYPE> dataInstance();

    TYPE impl = defaultImpl();
    private final LazyOptional<TYPE> cap = LazyOptional.of(() -> impl);

    @Override
    public CompoundTag serializeNBT() {
        return (CompoundTag) dataInstance().getStorage()
            .writeNBT(dataInstance(), impl, null);

    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        dataInstance().getStorage()
            .readNBT(dataInstance(), impl, null, nbt);

    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == dataInstance()) {
            return this.cap.cast();
        }
        return LazyOptional.empty();
    }
}